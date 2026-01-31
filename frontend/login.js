// ========== PARTICLE ANIMATION ==========
const canvas = document.getElementById('particles');
const ctx = canvas.getContext('2d');

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

const particles = [];
const particleCount = 100;

class Particle {
    constructor() {
        this.x = Math.random() * canvas.width;
        this.y = Math.random() * canvas.height;
        this.size = Math.random() * 2 + 1;
        this.speedX = Math.random() * 0.5 - 0.25;
        this.speedY = Math.random() * 0.5 - 0.25;
        this.opacity = Math.random() * 0.5;
    }

    update() {
        this.x += this.speedX;
        this.y += this.speedY;

        if (this.x > canvas.width) this.x = 0;
        if (this.x < 0) this.x = canvas.width;
        if (this.y > canvas.height) this.y = 0;
        if (this.y < 0) this.y = canvas.height;
    }

    draw() {
        ctx.fillStyle = `rgba(212, 175, 55, ${this.opacity})`;
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        ctx.fill();
    }
}

function initParticles() {
    for (let i = 0; i < particleCount; i++) {
        particles.push(new Particle());
    }
}

function animateParticles() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    particles.forEach(particle => {
        particle.update();
        particle.draw();
    });
    requestAnimationFrame(animateParticles);
}

window.addEventListener('resize', () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
});

initParticles();
animateParticles();

// ========== COUNTDOWN TIMER ==========
let timeRemaining = 60 * 60; // 60 minutes in seconds

function updateTimer() {
    const hours = Math.floor(timeRemaining / 3600);
    const minutes = Math.floor((timeRemaining % 3600) / 60);
    const seconds = timeRemaining % 60;

    document.getElementById('hours').textContent = String(hours).padStart(2, '0');
    document.getElementById('minutes').textContent = String(minutes).padStart(2, '0');
    document.getElementById('seconds').textContent = String(seconds).padStart(2, '0');

    if (timeRemaining > 0) {
        timeRemaining--;
    } else {
        // Timer expired
        showError('⏰ TIME\'S UP! The room is locked forever...');
    }
}

setInterval(updateTimer, 1000);

// ========== PASSWORD TOGGLE ==========
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const toggleBtn = document.querySelector('.toggle-password');
    
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleBtn.textContent = '🙈';
    } else {
        passwordInput.type = 'password';
        toggleBtn.textContent = '👁️';
    }
}

// ========== FORM VALIDATION & SUBMISSION ==========
const loginForm = document.getElementById('login-form');
const errorMessage = document.getElementById('error-message');

loginForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // Clear previous errors
    errorMessage.classList.remove('show');
    errorMessage.textContent = '';

    // Validation
    if (!username || !password) {
        showError('⚠️ Both username and password are required!');
        return;
    }

    if (username.length < 3) {
        showError('⚠️ Username must be at least 3 characters!');
        return;
    }

    if (password.length < 6) {
        showError('⚠️ Password must be at least 6 characters!');
        return;
    }

    // Simulate authentication (accept any valid credentials)
    authenticateUser(username, password);
});

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.classList.add('show');
}

function authenticateUser(username, password) {
    // Valid credentials
    const validUsers = {
        'salma': '123456',
        'marwa': 'marwa'
    };

    // Show loading state
    const submitBtn = document.querySelector('.submit-btn');
    const originalText = submitBtn.innerHTML;
    submitBtn.innerHTML = '<span class="btn-text">UNLOCKING...</span> <span class="btn-icon">⏳</span>';
    submitBtn.disabled = true;

    // Simulate API call delay
    setTimeout(() => {
        // Check credentials
        if (validUsers[username.toLowerCase()] === password) {
            // Store user session
            sessionStorage.setItem('escapeRoomUser', username);
            sessionStorage.setItem('escapeRoomLoginTime', new Date().toISOString());

            // Success animation
            submitBtn.innerHTML = '<span class="btn-text">ACCESS GRANTED!</span> <span class="btn-icon">✅</span>';
            submitBtn.style.background = 'linear-gradient(135deg, #10b981 0%, #059669 100%)';

            // Redirect to main application
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 1500);
        } else {
            // Failed authentication
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
            submitBtn.style.background = 'linear-gradient(135deg, var(--blood-red) 0%, var(--rust-orange) 100%)';

            showError('❌ Invalid credentials! Only authorized agents can enter.');

            // Shake animation
            submitBtn.style.animation = 'shake 0.5s ease';
            setTimeout(() => {
                submitBtn.style.animation = '';
            }, 500);
        }
    }, 2000);
}

// ========== INITIALIZE ==========
document.addEventListener('DOMContentLoaded', function() {
    // Check if already logged in
    const user = sessionStorage.getItem('escapeRoomUser');
    if (user) {
        window.location.href = 'index.html';
    }
});

