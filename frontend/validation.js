// ========== TEST DATA MANAGEMENT ==========
let testHistory = JSON.parse(localStorage.getItem('escapeRoomTestHistory')) || [];
let currentValidatedClient = localStorage.getItem('lastValidatedClient') || 'Anonymous';

function logTestData(entity, service, type, status, details) {
    // If we're validating a client, update the current client context
    if (service === 'Client' && status === 'PASS') {
        currentValidatedClient = entity;
        localStorage.setItem('lastValidatedClient', currentValidatedClient);
    }

    const entry = {
        id: Date.now(),
        client: currentValidatedClient,
        entity: entity || 'N/A',
        service: service,
        type: type, // EP, BVA, Decision Table, etc.
        status: status, // PASS or FAIL
        details: details,
        timestamp: new Date().toLocaleString()
    };
    testHistory.push(entry);
    localStorage.setItem('escapeRoomTestHistory', JSON.stringify(testHistory));
    updateFinalDashboard();
}

function clearTestData() {
    if (confirm('⚠️ Clear all accumulated test data?')) {
        testHistory = [];
        currentValidatedClient = 'Anonymous';
        localStorage.removeItem('escapeRoomTestHistory');
        localStorage.removeItem('lastValidatedClient');
        updateFinalDashboard();
    }
}

function exportTestData() {
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(testHistory, null, 2));
    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download", "escape_room_test_results.json");
    document.body.appendChild(downloadAnchorNode);
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
}

function updateFinalDashboard() {
    const body = document.getElementById('summary-body');
    const clientReports = document.getElementById('client-reports');
    if (!body) return;

    if (testHistory.length === 0) {
        body.innerHTML = '<tr><td colspan="5" class="empty-state">No test data collected yet. Start validating components!</td></tr>';
        clientReports.innerHTML = '';
        updateStats();
        return;
    }

    // Update Table
    body.innerHTML = testHistory.slice().reverse().map(test => `
        <tr>
            <td>
                <strong>${test.client}</strong><br>
                <small style="color: var(--text-secondary)">Testing: ${test.entity}</small>
            </td>
            <td>${test.service}</td>
            <td><small>${test.type}</small></td>
            <td><span class="status-badge ${test.status === 'PASS' ? 'pass' : 'fail'}">${test.status}</span></td>
            <td><small>${test.timestamp}</small></td>
        </tr>
    `).join('');

    // Update Stats
    updateStats();

    // Group by Client for Detailed Reports
    renderClientReports();
}

function updateStats() {
    const clients = new Set(testHistory.filter(t => t.service === 'Client' && t.status === 'PASS').map(t => t.entity)).size;
    const rooms = new Set(testHistory.filter(t => t.service === 'Room' && t.status === 'PASS').map(t => t.entity)).size;
    const reservations = testHistory.filter(t => t.service === 'Reservation' && t.status === 'PASS').length;

    document.getElementById('stat-clients').textContent = clients;
    document.getElementById('stat-rooms').textContent = rooms;
    document.getElementById('stat-reservations').textContent = reservations;
    document.getElementById('stat-total-tests').textContent = testHistory.length;
}

function renderClientReports() {
    const container = document.getElementById('client-reports');
    if (!container) return;

    // Group tests by Client
    const clients = Array.from(new Set(testHistory.map(t => t.client)));

    let html = '<h3>👤 Activity by Client</h3>';

    clients.forEach(clientId => {
        const clientTests = testHistory.filter(t => t.client === clientId);
        const passCount = clientTests.filter(t => t.status === 'PASS').length;

        html += `
            <div class="client-report-card">
                <div class="report-header">
                    <h4>Client: ${clientId}</h4>
                    <span class="client-tag">${clientTests.length} Total Tests</span>
                </div>
                <div class="report-body">
                    <div class="report-section">
                        <h5>Session Summary</h5>
                        <div class="report-data">
                            <div class="data-row">
                                <span class="data-label">Success Rate:</span>
                                <span class="data-value">${Math.round((passCount / clientTests.length) * 100)}%</span>
                            </div>
                            <div class="data-row">
                                <span class="data-label">Last Activity:</span>
                                <span class="data-value">${clientTests[clientTests.length - 1].timestamp.split(',')[1]}</span>
                            </div>
                        </div>
                    </div>
                    <div class="report-section">
                        <h5>Services Tested</h5>
                        <div class="report-data">
                            ${Array.from(new Set(clientTests.map(t => t.service))).map(s => `
                                <div class="data-row">
                                    <span class="data-label">${s}:</span>
                                    <span class="data-value">${clientTests.filter(t => t.service === s && t.status === 'PASS').length} Pass</span>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                    <div class="report-section">
                        <h5>Detailed Log</h5>
                        <div style="max-height: 100px; overflow-y: auto; font-size: 0.8rem;">
                            ${clientTests.slice().reverse().map(t => `
                                <div style="margin-bottom: 5px; border-bottom: 1px solid rgba(255,255,255,0.05);">
                                    [${t.status}] ${t.service} - ${t.type}
                                </div>
                            `).join('')}
                        </div>
                    </div>
                </div>
            </div>
        `;
    });

    container.innerHTML = html;
}


// ========== SESSION MANAGEMENT ==========
function checkSession() {
    const user = sessionStorage.getItem('escapeRoomUser');
    if (!user) {
        window.location.href = 'login.html';
        return false;
    }

    // Display username
    const usernameDisplay = document.getElementById('username-display');
    if (usernameDisplay) {
        usernameDisplay.textContent = user;
    }

    return true;
}

function logout() {
    if (confirm('⚠️ Are you sure you want to exit the room? All progress will be lost!')) {
        sessionStorage.removeItem('escapeRoomUser');
        sessionStorage.removeItem('escapeRoomLoginTime');
        window.location.href = 'login.html';
    }
}

// ========== TAB NAVIGATION ==========
function showTab(tabName, event) {
    // Hide all tabs
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));

    // Remove active class from all buttons
    const buttons = document.querySelectorAll('.tab-btn');
    buttons.forEach(btn => btn.classList.remove('active'));

    // Show selected tab
    const selectedTab = document.getElementById(tabName + '-tab');
    if (selectedTab) {
        selectedTab.classList.add('active');
    }

    // Add active class to clicked button
    if (event && event.currentTarget) {
        event.currentTarget.classList.add('active');
    } else {
        // Fallback: find button by tab name
        buttons.forEach(btn => {
            if (btn.textContent.toLowerCase().includes(tabName)) {
                btn.classList.add('active');
            }
        });
    }
}

// ========== CLIENT VALIDATION ==========
function validateClient(event) {
    event.preventDefault();

    const name = document.getElementById('client-name').value;
    const email = document.getElementById('client-email').value;
    const age = parseInt(document.getElementById('client-age').value);

    const errors = [];
    const warnings = [];

    // Name validation (EP1-EP4)
    if (!name) {
        errors.push('Name is required (EP3: Null value)');
    } else if (name.trim() === '') {
        errors.push('Name cannot be empty (EP2: Empty string)');
    } else if (name.length > 100) {
        errors.push('Name must not exceed 100 characters (EP4: String > 100 chars)');
    }

    // Email validation (EP5-EP9)
    if (!email) {
        errors.push('Email is required (EP8: Null value)');
    } else if (email.trim() === '') {
        errors.push('Email cannot be empty (EP9: Empty string)');
    } else if (!email.includes('@')) {
        errors.push('Email format is invalid - missing @ symbol (EP6: No @)');
    } else if (email.endsWith('@')) {
        errors.push('Email format is invalid - missing domain (EP7: No domain)');
    }

    // Age validation (EP10-EP14)
    if (isNaN(age)) {
        errors.push('Age is required (EP13: Null value)');
    } else if (age < 0) {
        errors.push('Age must be positive (EP14: Negative value)');
    } else if (age < 18) {
        errors.push('Age must be at least 18 (EP11: Age < 18)');
    } else if (age > 60) {
        errors.push('Age must not exceed 60 (EP12: Age > 60)');
    }

    // BVA warnings
    if (age === 18 || age === 19) {
        warnings.push('BVA: Testing minimum boundary (18, 19)');
    }
    if (age === 60 || age === 61) {
        warnings.push('BVA: Testing maximum boundary (60, 61)');
    }

    displayResult('client-result', errors, warnings, 'Client');

    // LOG DATA
    logTestData(
        name || 'Unknown',
        'Client',
        'Equivalence Partitioning / BVA',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { email, age, errors }
    );
}

// ========== ROOM ANIMATION FUNCTIONS ==========
function updateRoomPreview() {
    const capacity = parseInt(document.getElementById('room-capacity').value) || 0;
    const level = document.getElementById('room-level').value;
    const availability = document.getElementById('room-availability').checked;

    // Update status display
    const capacityDisplay = document.getElementById('capacity-display');
    const difficultyDisplay = document.getElementById('difficulty-display');
    const statusDisplay = document.getElementById('room-status-display');

    if (capacityDisplay) capacityDisplay.textContent = capacity + ' players';
    if (difficultyDisplay) difficultyDisplay.textContent = level || 'Unknown';
    if (statusDisplay) statusDisplay.textContent = availability ? 'Available' : 'Locked';

    // Animate door based on availability
    const door = document.getElementById('animated-door');
    if (door) {
        if (availability) {
            door.classList.add('door-open');
            door.classList.remove('door-locked');
        } else {
            door.classList.remove('door-open');
            door.classList.add('door-locked');
        }
    }

    // Change room atmosphere based on difficulty
    const room3d = document.querySelector('.room-3d');
    if (room3d) {
        room3d.className = 'room-3d';
        if (level === 'EASY') {
            room3d.classList.add('room-easy');
        } else if (level === 'MEDIUM') {
            room3d.classList.add('room-medium');
        } else if (level === 'HARD') {
            room3d.classList.add('room-hard');
        }
    }
}

function adjustCapacity(delta) {
    const input = document.getElementById('room-capacity');
    let value = parseInt(input.value) || 2;
    value = Math.max(2, Math.min(10, value + delta));
    input.value = value;
    updateRoomPreview();
}

function selectDifficulty(level) {
    // Update hidden select
    document.getElementById('room-level').value = level;

    // Update button states
    document.querySelectorAll('.difficulty-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    const selectedBtn = document.querySelector(`[data-level="${level}"]`);
    if (selectedBtn) {
        selectedBtn.classList.add('active');
    }

    updateRoomPreview();
}

// ========== ROOM VALIDATION ==========
function validateRoom(event) {
    event.preventDefault();

    const name = document.getElementById('room-name').value;
    const capacity = parseInt(document.getElementById('room-capacity').value);
    const level = document.getElementById('room-level').value;
    const availability = document.getElementById('room-availability').checked;

    const errors = [];
    const warnings = [];

    // Name validation (EP15-EP18)
    if (!name) {
        errors.push('Name is required (EP17: Null value)');
    } else if (name.trim() === '') {
        errors.push('Name cannot be empty (EP16: Empty string)');
    } else if (name.length > 50) {
        errors.push('Name must not exceed 50 characters (EP18: String > 50 chars)');
    }

    // Capacity validation (EP19-EP23)
    if (isNaN(capacity)) {
        errors.push('Capacity is required (EP22: Null value)');
    } else if (capacity <= 0) {
        errors.push('Capacity must be positive (EP23: Zero or negative)');
    } else if (capacity < 2) {
        errors.push('Capacity must be at least 2 (EP20: Capacity < 2)');
    } else if (capacity > 10) {
        errors.push('Capacity must not exceed 10 (EP21: Capacity > 10)');
    }

    // Level validation (EP24-EP27)
    if (!level) {
        errors.push('Level is required (EP26: Null value)');
    } else if (level !== 'EASY' && level !== 'MEDIUM' && level !== 'HARD') {
        errors.push('Invalid level - must be EASY, MEDIUM, or HARD (EP25: Invalid level)');
    }

    // BVA warnings
    if (capacity === 2 || capacity === 3) {
        warnings.push('BVA: Testing minimum boundary (2, 3)');
    }
    if (capacity === 9 || capacity === 10) {
        warnings.push('BVA: Testing maximum boundary (9, 10)');
    }

    // Animate validation result
    if (errors.length === 0) {
        const door = document.getElementById('animated-door');
        if (door) {
            door.style.animation = 'doorSuccess 1s ease';
            setTimeout(() => {
                door.style.animation = '';
            }, 1000);
        }
    }

    displayResult('room-result', errors, warnings, 'Room');

    // LOG DATA
    logTestData(
        name || 'Unknown Room',
        'Room',
        'Equivalence Partitioning / BVA',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { capacity, level, availability, errors }
    );
}

// ========== RESERVATION VALIDATION (Decision Table) ==========
function validateReservation(event) {
    event.preventDefault();

    const clientAge = parseInt(document.getElementById('res-client-age').value);
    const roomCapacity = parseInt(document.getElementById('res-room-capacity').value);
    const roomAvailable = document.getElementById('res-room-available').checked;
    const dateInput = document.getElementById('res-date').value;
    const numberOfPlayers = parseInt(document.getElementById('res-players').value);

    const errors = [];
    const warnings = [];
    const conditions = [];

    // Decision Table Conditions
    const c1 = roomAvailable;
    const c2 = dateInput && new Date(dateInput) > new Date();
    const c3 = !isNaN(numberOfPlayers) && !isNaN(roomCapacity) && numberOfPlayers <= roomCapacity;
    const c4 = !isNaN(clientAge) && clientAge >= 18;

    conditions.push(`C1 (Room Available): ${c1 ? '✓' : '✗'}`);
    conditions.push(`C2 (Future Date): ${c2 ? '✓' : '✗'}`);
    conditions.push(`C3 (Players ≤ Capacity): ${c3 ? '✓' : '✗'}`);
    conditions.push(`C4 (Client Age ≥ 18): ${c4 ? '✓' : '✗'}`);

    // Validation
    if (!roomAvailable) {
        errors.push('Room is unavailable (Decision Table Rule R5)');
    }
    if (!c2) {
        errors.push('Date must be in the future (Decision Table Rule R4)');
    }
    if (!c3) {
        if (isNaN(numberOfPlayers)) {
            errors.push('Number of players is required');
        } else if (numberOfPlayers > roomCapacity) {
            errors.push('Number of players exceeds room capacity (Decision Table Rule R3)');
        }
    }
    if (!c4) {
        if (isNaN(clientAge)) {
            errors.push('Client age is required');
        } else {
            errors.push('Client must be at least 18 years old (Decision Table Rule R2)');
        }
    }

    if (c1 && c2 && c3 && c4) {
        warnings.push('✓ All conditions met - Reservation can be created (Decision Table Rule R1)');
    }

    displayResultWithConditions('reservation-result', errors, warnings, conditions, 'Reservation');

    // LOG DATA
    logTestData(
        `Age: ${clientAge}, Players: ${numberOfPlayers}`,
        'Reservation',
        'Decision Table',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { date: dateInput, errors }
    );
}

// ========== GAME VALIDATION ==========
function validateGame(event) {
    event.preventDefault();

    const duration = parseInt(document.getElementById('game-duration').value);
    const score = parseInt(document.getElementById('game-score').value);
    const state = document.getElementById('game-state').value;

    const errors = [];
    const warnings = [];

    // Duration validation (EP49-EP53)
    if (isNaN(duration)) {
        errors.push('Duration is required (EP52: Null value)');
    } else if (duration <= 0) {
        errors.push('Duration must be positive (EP53: Zero or negative)');
    } else if (duration < 30) {
        errors.push('Duration must be at least 30 minutes (EP50: Duration < 30)');
    } else if (duration > 90) {
        errors.push('Duration must not exceed 90 minutes (EP51: Duration > 90)');
    }

    // Score validation (EP54-EP57)
    if (isNaN(score)) {
        errors.push('Score is required (EP57: Null value)');
    } else if (score < 50) {
        errors.push('Score is too low - must be at least 50 (EP55: Score < 50)');
    }

    // State validation (EP58-EP61)
    if (!state) {
        errors.push('State is required (EP60: Null value)');
    } else if (!['NOT_STARTED', 'IN_PROGRESS', 'PAUSED', 'COMPLETED', 'FAILED'].includes(state)) {
        errors.push('Invalid game state (EP59: Invalid state)');
    }

    // BVA warnings
    if (duration === 30 || duration === 31) {
        warnings.push('BVA: Testing minimum duration boundary (30, 31)');
    }
    if (duration === 89 || duration === 90) {
        warnings.push('BVA: Testing maximum duration boundary (89, 90)');
    }

    displayResult('game-result', errors, warnings, 'Game');

    // LOG DATA
    logTestData(
        `Dur: ${duration}, Score: ${score}`,
        'Game',
        'Equivalence Partitioning',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { state, errors }
    );
}

// ========== GAME COMPLETION DECISION TABLE ==========
function evaluateGameCompletion() {
    const timeRemaining = parseInt(document.getElementById('dt-time').value);
    const allPuzzlesSolved = document.getElementById('dt-puzzles').checked;
    const score = parseInt(document.getElementById('dt-score').value);

    const errors = [];
    const warnings = [];
    const conditions = [];

    const c1 = !isNaN(timeRemaining) && timeRemaining > 0;
    const c2 = allPuzzlesSolved;
    const c3 = !isNaN(score) && score >= 600;

    conditions.push(`C1 (Time Remaining > 0): ${c1 ? '✓' : '✗'}`);
    conditions.push(`C2 (All Puzzles Solved): ${c2 ? '✓' : '✗'}`);
    conditions.push(`C3 (Score ≥ 600): ${c3 ? '✓' : '✗'}`);

    let result = '';

    if (c1 && c2 && c3) {
        result = 'SUCCESS - Game completed successfully! (Rule R1)';
        warnings.push(result);
    } else if (c1 && c2 && !c3) {
        result = 'FAILURE (Low Score) - All puzzles solved but score < 600 (Rule R2)';
        errors.push(result);
    } else if (!c1 && c2) {
        result = 'FAILURE (Timeout) - Time ran out (Rule R3)';
        errors.push(result);
    } else if (!c1 && !c2) {
        result = 'FAILURE (Timeout) - Time ran out, puzzles incomplete (Rule R4)';
        errors.push(result);
    } else if (c1 && !c2) {
        result = 'IN PROGRESS - Game still ongoing (Rule R5)';
        warnings.push(result);
    }

    displayResultWithConditions('dt-result', errors, warnings, conditions, 'Game Completion');

    // LOG DATA
    logTestData(
        `Score: ${score}`,
        'Game',
        'Decision Table (Completion)',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { result, errors }
    );
}

// ========== SCORE VALIDATION ==========
function validateScore(event) {
    event.preventDefault();

    const points = parseInt(document.getElementById('score-points').value);
    const successRadio = document.querySelector('input[name="score-success"]:checked');

    const errors = [];
    const warnings = [];

    // Points validation (EP62-EP65)
    if (isNaN(points)) {
        errors.push('Points are required (EP65: Null value)');
    } else if (points < 50) {
        errors.push('Score is too low - must be at least 50 (EP63: Score < 50)');
    }

    // Success validation (EP66-EP68)
    if (!successRadio) {
        errors.push('Success status is required (EP68: Null value)');
    }

    // BVA warnings
    if (points === 49 || points === 50) {
        warnings.push('BVA: Testing score boundary (49, 50)');
    }

    displayResult('score-result', errors, warnings, 'Score');

    // LOG DATA
    logTestData(
        `Points: ${points}`,
        'Score',
        'Equivalence Partitioning / BVA',
        errors.length === 0 ? 'PASS' : 'FAIL',
        { success: successRadio ? successRadio.value : null, errors }
    );
}

// ========== RESERVATION STATE TRANSITIONS ==========
let currentReservationState = 'PENDING';

function testReservationState(targetState) {
    const validTransitions = {
        'PENDING': ['CONFIRMED', 'CANCELLED'],
        'CONFIRMED': ['CANCELLED', 'COMPLETED'],
        'CANCELLED': [],
        'COMPLETED': []
    };

    const errors = [];
    const warnings = [];

    if (validTransitions[currentReservationState].includes(targetState)) {
        warnings.push(`✓ Valid transition: ${currentReservationState} → ${targetState}`);
        currentReservationState = targetState;
    } else {
        errors.push(`✗ Invalid transition: ${currentReservationState} → ${targetState}`);

        // Provide specific error messages
        if (currentReservationState === 'PENDING' && targetState === 'COMPLETED') {
            errors.push('Cannot complete unconfirmed reservation (IT1)');
        } else if (currentReservationState === 'CANCELLED' && targetState === 'CONFIRMED') {
            errors.push('Cannot confirm cancelled reservation (IT2)');
        } else if (currentReservationState === 'CANCELLED' && targetState === 'COMPLETED') {
            errors.push('Cannot complete cancelled reservation (IT3)');
        } else if (currentReservationState === 'COMPLETED' && targetState === 'CANCELLED') {
            errors.push('Cannot cancel completed reservation (IT4)');
        } else if (currentReservationState === 'COMPLETED' && targetState === 'CONFIRMED') {
            errors.push('Cannot re-confirm completed reservation (IT5)');
        }
    }

    warnings.push(`Current State: ${currentReservationState}`);
    displayResult('state-result', errors, warnings, 'State Transition');
}

// ========== RESULT DISPLAY FUNCTIONS ==========
function displayResult(elementId, errors, warnings, entityName) {
    const resultDiv = document.getElementById(elementId);
    resultDiv.innerHTML = '';

    if (errors.length === 0) {
        resultDiv.className = 'result success show';
        resultDiv.innerHTML = `<h3>✓ ${entityName} Validation Passed</h3>`;
    } else {
        resultDiv.className = 'result error show';
        resultDiv.innerHTML = `<h3>✗ ${entityName} Validation Failed</h3><ul>${errors.map(e => `<li>${e}</li>`).join('')}</ul>`;
    }

    // Dynamic Execution Trace
    let traceRows = [];
    if (entityName === 'Client') {
        traceRows = [
            { p: 'Name', v: document.getElementById('client-name').value || 'null', rule: 'EP1-EP4', s: errors.some(e => e.toLowerCase().includes('name')) ? 'invalid' : 'valid' },
            { p: 'Email', v: document.getElementById('client-email').value || 'null', rule: 'EP5-EP9', s: errors.some(e => e.toLowerCase().includes('email')) ? 'invalid' : 'valid' },
            { p: 'Age', v: document.getElementById('client-age').value || 'null', rule: 'EP10-EP14 / BVA', s: errors.some(e => e.toLowerCase().includes('age')) ? 'invalid' : 'valid' }
        ];
    } else if (entityName === 'Room') {
        traceRows = [
            { p: 'Room Name', v: document.getElementById('room-name').value || 'null', rule: 'EP15-EP18', s: errors.some(e => e.toLowerCase().includes('name')) ? 'invalid' : 'valid' },
            { p: 'Capacity', v: document.getElementById('room-capacity').value || 'null', rule: 'EP19-EP23 / BVA', s: errors.some(e => e.toLowerCase().includes('capacity')) ? 'invalid' : 'valid' }
        ];
    } else if (entityName === 'Game') {
        traceRows = [
            { p: 'Duration', v: document.getElementById('game-duration').value || 'null', rule: 'EP49-EP53 / BVA', s: errors.some(e => e.toLowerCase().includes('duration')) ? 'invalid' : 'valid' },
            { p: 'Score', v: document.getElementById('game-score').value || 'null', rule: 'EP54-EP57', s: errors.some(e => e.toLowerCase().includes('score')) ? 'invalid' : 'valid' }
        ];
    } else if (entityName === 'Score') {
        const pts = document.getElementById('score-points').value;
        const successRadio = document.querySelector('input[name="score-success"]:checked');
        traceRows = [
            { p: 'Points', v: pts || 'null', rule: 'EP62-EP65 / BVA', s: errors.some(e => e.toLowerCase().includes('points')) ? 'invalid' : 'valid' },
            { p: 'Success Status', v: successRadio ? successRadio.value.toUpperCase() : 'null', rule: 'EP66-EP68', s: errors.some(e => e.toLowerCase().includes('success')) ? 'invalid' : 'valid' }
        ];
    }
    updateExecutionTrace(traceRows);
}

function displayResultWithConditions(elementId, errors, warnings, conditions, entityName) {
    const resultDiv = document.getElementById(elementId);
    resultDiv.innerHTML = '';

    if (errors.length === 0) {
        resultDiv.className = 'result success show';
        resultDiv.innerHTML = `<h3>✓ ${entityName} Validation Passed</h3>`;
    } else {
        resultDiv.className = 'result error show';
        resultDiv.innerHTML = `<h3>✗ ${entityName} Validation Failed</h3><ul>${errors.map(e => `<li>${e}</li>`).join('')}</ul>`;
    }

    const traceRows = [];
    if (entityName === 'Reservation') {
        const age = document.getElementById('res-client-age').value;
        const players = document.getElementById('res-players').value;
        traceRows.push({ p: 'Client Age', v: age || 'null', rule: 'EP: ≥ 18', s: age >= 18 ? 'valid' : 'invalid' });
        traceRows.push({ p: 'Players', v: players || 'null', rule: 'Rule: ≤ Capacity', s: errors.some(e => e.includes('capacity')) ? 'invalid' : 'valid' });
        traceRows.push({ p: 'Outcome', v: '-', rule: 'Decision Table', s: errors.length === 0 ? 'valid' : 'invalid' });
    }
    updateExecutionTrace(traceRows);
}

function updateExecutionTrace(rows) {
    const dashboard = document.getElementById('dynamic-dashboard');
    const body = document.getElementById('exec-body');

    if (rows.length === 0) {
        dashboard.style.display = 'none';
        return;
    }

    dashboard.style.display = 'block';
    body.innerHTML = rows.map(row => `
        <tr>
            <td>${row.p}</td>
            <td><code>${row.v}</code></td>
            <td>${row.rule}</td>
            <td><span class="val-status ${row.s}">${row.s.toUpperCase()}</span></td>
        </tr>
    `).join('');
}

// ========== INITIALIZE ==========
document.addEventListener('DOMContentLoaded', function () {
    // Check session on page load
    checkSession();

    // Set minimum date for reservation to tomorrow
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const dateInput = document.getElementById('res-date');
    if (dateInput) {
        dateInput.min = tomorrow.toISOString().slice(0, 16);
    }

    // Initialize Dashboard
    updateFinalDashboard();
});
