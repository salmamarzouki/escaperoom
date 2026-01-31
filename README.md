# 🔐 Escape Room Management - Black-Box Testing Project

## 📋 Project Overview

This is a comprehensive **Black-Box Testing** academic project for an Escape Room Management Application. The project demonstrates the application of four key testing techniques taught in software testing courses:

1. **Equivalence Partitioning (EP)**
2. **Boundary Value Analysis (BVA)**
3. **Decision Table Testing**
4. **State Transition Testing**

---

## 🎯 Project Objectives

✅ Identify and test **BLACK-BOX** test cases only (no white-box testing)  
✅ Apply course-taught techniques with proper terminology  
✅ Generate test cases compliant with **Each Choice Coverage**  
✅ Implement test cases using **JUnit 5**  
✅ Provide a simple **front-end** to validate test cases  

---

## 📁 Project Structure

```
escapeRoom/
├── src/
│   ├── main/java/org/example/escaperoom/
│   │   └── entities/
│   │       ├── Client.java          # Client entity (id, name, email, age)
│   │       ├── Room.java            # Room entity (id, name, capacity, level, availability)
│   │       ├── Reservation.java     # Reservation entity with state transitions
│   │       ├── Game.java            # Game entity with state machine
│   │       └── Score.java           # Score entity (points, success)
│   │
│   └── test/java/org/example/escaperoom/blackbox/
│       ├── ClientBlackBoxTest.java       # 16 test cases (EP + BVA)
│       ├── RoomBlackBoxTest.java         # 17 test cases (EP + BVA)
│       ├── ReservationBlackBoxTest.java  # 27 test cases (EP + DT + ST)
│       ├── GameBlackBoxTest.java         # 35 test cases (EP + BVA + DT + ST)
│       └── ScoreBlackBoxTest.java        # 12 test cases (EP + BVA)
│
├── frontend/
│   ├── index.html          # Interactive testing interface
│   ├── styles.css          # Premium styling with animations
│   └── validation.js       # Validation logic for all services
│
├── BLACK_BOX_TESTING_DOCUMENTATION.md  # Complete testing documentation
└── README.md               # This file
```

---

## 📊 Test Coverage Summary

| Service | EP Tests | BVA Tests | Decision Table | State Transition | **Total** |
|---------|----------|-----------|----------------|------------------|-----------|
| **Client** | 12 | 4 | - | - | **16** |
| **Room** | 13 | 4 | - | - | **17** |
| **Reservation** | 13 | - | 5 | 9 | **27** |
| **Game** | 12 | 4 | 5 | 14 | **35** |
| **Score** | 8 | 4 | - | - | **12** |
| **TOTAL** | **58** | **16** | **10** | **23** | **107** |

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven** 3.6+
- A modern web browser (for front-end)

### Running the JUnit Tests

1. **Navigate to the project directory:**
   ```bash
   cd c:\Users\sirin\Downloads\escapeRoom\escapeRoom
   ```

2. **Run all tests:**
   ```bash
   mvn test
   ```

3. **Run specific test class:**
   ```bash
   mvn test -Dtest=ClientBlackBoxTest
   mvn test -Dtest=RoomBlackBoxTest
   mvn test -Dtest=ReservationBlackBoxTest
   mvn test -Dtest=GameBlackBoxTest
   mvn test -Dtest=ScoreBlackBoxTest
   ```

4. **View test results:**
   Test results will be displayed in the console and saved in `target/surefire-reports/`

### Running the Front-End

1. **Open the front-end:**
   - Navigate to `frontend/` folder
   - Open `index.html` in your web browser

2. **Test each service:**
   - Use the tabs to switch between services
   - Fill in the forms with valid/invalid data
   - Click "Validate" to see results
   - Test state transitions and decision tables

---

## 📖 Testing Techniques Explained

### 1️⃣ Equivalence Partitioning (EP)

Divides input domains into **valid** and **invalid** partitions.

**Example - Client Age:**
- **EP10 (Valid):** Age 18-99
- **EP11 (Invalid):** Age < 18
- **EP12 (Invalid):** Age > 99
- **EP13 (Invalid):** Null value
- **EP14 (Invalid):** Negative value

**Rule:** One test case per partition, invalid partitions tested individually.

### 2️⃣ Boundary Value Analysis (BVA)

Tests values at boundaries using **2-value BVA**.

**Example - Room Capacity (2-10):**
- **Minimum boundary:** 2, 3
- **Maximum boundary:** 9, 10

**Rule:** Test min, min+1, max-1, max for each boundary.

### 3️⃣ Decision Table Testing

Tests combinations of conditions and their resulting actions.

**Example - Reservation Validation:**

| Conditions | R1 | R2 | R3 | R4 | R5 |
|------------|----|----|----|----|-----|
| Room Available | ✓ | ✓ | ✓ | ✓ | ✗ |
| Future Date | ✓ | ✓ | ✓ | ✗ | ✓ |
| Players ≤ Capacity | ✓ | ✓ | ✗ | ✓ | ✓ |
| Client Age ≥ 18 | ✓ | ✗ | ✓ | ✓ | ✓ |
| **Action** | Create | Reject | Reject | Reject | Reject |

### 4️⃣ State Transition Testing

Tests valid and invalid state transitions.

**Example - Reservation States:**

```
[PENDING] --confirm()--> [CONFIRMED] --complete()--> [COMPLETED]
    |                         |
    +----cancel()----+----cancel()----+
                     ↓
                [CANCELLED]
```

**Valid Transitions:**
- PENDING → CONFIRMED
- PENDING → CANCELLED
- CONFIRMED → CANCELLED
- CONFIRMED → COMPLETED

**Invalid Transitions:**
- PENDING → COMPLETED ❌
- CANCELLED → CONFIRMED ❌
- COMPLETED → CANCELLED ❌

---

## 🧪 Test Case Examples

### Client Service - TC-CLIENT-01
```java
@Test
@DisplayName("TC-CLIENT-01: Valid client creation with all valid inputs")
public void testValidClientCreation() {
    Client client = new Client("John Doe", "john@example.com", 25);
    
    assertNotNull(client);
    assertEquals("John Doe", client.getName());
    assertEquals("john@example.com", client.getEmail());
    assertEquals(25, client.getAge());
}
```

### Game Service - TC-ST-GAME-01
```java
@Test
@DisplayName("TC-ST-GAME-01: NOT_STARTED -> IN_PROGRESS (Valid Transition)")
public void testStateTransition_NotStartedToInProgress() {
    Game game = new Game(null, 60);
    assertEquals(GameState.NOT_STARTED, game.getState());
    
    game.start();
    
    assertEquals(GameState.IN_PROGRESS, game.getState());
    assertNotNull(game.getStartedAt());
}
```

---

## 🎨 Front-End Features

### Interactive Forms
- ✅ **Client Registration:** Test name, email, age validation
- ✅ **Room Configuration:** Test capacity, level, availability
- ✅ **Reservation Booking:** Test decision table rules
- ✅ **Game Session:** Test duration, score, state
- ✅ **Score Recording:** Test points and success status

### Visual Feedback
- 🟢 **Success:** Green background with checkmark
- 🔴 **Error:** Red background with error details
- 📊 **Conditions:** Display decision table conditions
- 🔄 **State Transitions:** Interactive state machine

### Design Features
- 🎨 Modern gradient background
- ✨ Smooth animations
- 📱 Responsive design
- 🎯 Clear error messages with EP/BVA references

---

## 📚 Documentation

### Complete Documentation
See `BLACK_BOX_TESTING_DOCUMENTATION.md` for:
- Detailed test case specifications
- Equivalence partition tables
- Boundary value analysis tables
- Decision tables with rules
- State transition diagrams
- Coverage analysis

### Test Case Naming Convention
```
TC-<SERVICE>-<NUMBER>: <Description>
TC-BVA-<SERVICE>-<NUMBER>: <BVA Description>
TC-DT-<NUMBER>: <Decision Table Rule>
TC-ST-<SERVICE>-<NUMBER>: <State Transition>
```

---

## ✅ Academic Compliance

This project strictly adheres to academic requirements:

✅ **Black-Box Testing Only:** No white-box techniques used  
✅ **Course Terminology:** EP, BVA, TC, coverage  
✅ **One Test Per Partition:** Each partition has exactly one test case  
✅ **Invalid Partitions Separate:** No combination of invalid inputs  
✅ **Invalid Transitions Separate:** Each invalid transition tested individually  
✅ **No Advanced Concepts:** No mocks, stubs, or concepts not taught in course  

---

## 📊 Test Execution Results

Run the tests to see:
- ✅ All 107 test cases passing
- 📈 100% coverage of all partitions
- 🎯 100% coverage of all boundaries
- ✔️ 100% coverage of all decision rules
- 🔄 100% coverage of all state transitions

---

## 🎓 Learning Outcomes

By completing this project, you will:

1. ✅ Master **Equivalence Partitioning** technique
2. ✅ Apply **2-value Boundary Value Analysis**
3. ✅ Create and test **Decision Tables**
4. ✅ Design and validate **State Machines**
5. ✅ Write professional **JUnit 5** tests
6. ✅ Build interactive **validation interfaces**
7. ✅ Document testing processes academically

---

## 📝 Notes

- All test cases follow the **Each Choice Coverage** criterion
- Invalid partitions are **never combined** in a single test
- State transitions are tested with **complete sequences**
- Front-end provides **real-time validation** with partition references
- Documentation uses **academic style** with proper tables and diagrams

---

## 👨‍🎓 Author

**Software Testing Course Project**  
Black-Box Testing Techniques  
January 2026

---

## 📄 License

This project is created for educational purposes as part of a Software Testing course.

---

## 🆘 Support

For questions or issues:
1. Review the `BLACK_BOX_TESTING_DOCUMENTATION.md`
2. Check test case comments in Java files
3. Inspect front-end validation messages
4. Verify test execution with `mvn test -X` for debug output

---

**Happy Testing! 🧪✨**
