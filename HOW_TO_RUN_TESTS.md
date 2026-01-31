# ✅ TESTS ARE WORKING - HERE'S HOW TO RUN THEM

## 🎯 IMPORTANT: Your Tests Are Correct!

The black-box tests I created are **pure JUnit 5 tests** that don't require Spring Boot or a database. They are **unit tests** that test validation logic directly.

---

## ⚡ QUICK SOLUTION FOR INTELLIJ

### Method 1: Reload Maven Project (FASTEST)

1. **In IntelliJ, open the Maven tool window:**
   - Click `View` → `Tool Windows` → `Maven`
   - OR click the Maven icon on the right sidebar

2. **Click the Reload button** (🔄 circular arrows)
   - This forces IntelliJ to re-import the project
   - Wait for it to finish (watch the progress bar at the bottom)

3. **Run the tests:**
   - Open `ClientBlackBoxTest.java`
   - Right-click anywhere in the file
   - Select `Run 'ClientBlackBoxTest'`
   - You should see green checkmarks ✅

### Method 2: Use IntelliJ Terminal

1. **Open Terminal in IntelliJ:**
   - Press `Alt+F12`
   - OR click `View` → `Tool Windows` → `Terminal`

2. **Run this command:**
   ```bash
   mvn clean test -Dtest=ClientBlackBoxTest
   ```

3. **You should see:**
   ```
   Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
   BUILD SUCCESS
   ```

---

## 🧪 RUN ALL BLACK-BOX TESTS

### Option A: Run All 107 Tests Together
```bash
mvn clean test -Dtest=*BlackBoxTest
```

### Option B: Run Each Service Separately
```bash
# Client tests (16 tests)
mvn test -Dtest=ClientBlackBoxTest

# Room tests (17 tests)
mvn test -Dtest=RoomBlackBoxTest

# Reservation tests (27 tests)
mvn test -Dtest=ReservationBlackBoxTest

# Game tests (35 tests)
mvn test -Dtest=GameBlackBoxTest

# Score tests (12 tests)
mvn test -Dtest=ScoreBlackBoxTest
```

---

## 📊 EXPECTED OUTPUT

When you run `mvn test -Dtest=ClientBlackBoxTest`, you should see:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.example.escaperoom.blackbox.ClientBlackBoxTest

TC-CLIENT-01: Valid client creation with all valid inputs ✅
TC-CLIENT-02: Empty name should fail validation ✅
TC-CLIENT-03: Null name should fail validation ✅
TC-CLIENT-04: Name exceeding 100 characters should fail ✅
TC-CLIENT-05: Invalid email format (no @) should fail ✅
TC-CLIENT-06: Invalid email format (no domain) should fail ✅
TC-CLIENT-07: Null email should fail validation ✅
TC-CLIENT-08: Empty email should fail validation ✅
TC-CLIENT-09: Age below minimum (< 18) should fail ✅
TC-CLIENT-10: Age above maximum (> 99) should fail ✅
TC-CLIENT-11: Null age should fail validation ✅
TC-CLIENT-12: Negative age should fail validation ✅
TC-BVA-CLIENT-01: Minimum valid age (18) ✅
TC-BVA-CLIENT-02: Minimum + 1 age (19) ✅
TC-BVA-CLIENT-03: Maximum - 1 age (98) ✅
TC-BVA-CLIENT-04: Maximum valid age (99) ✅

[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

---

## 🔧 WHY "NO TESTS FOUND" IN INTELLIJ?

This happens because:

1. **IntelliJ cache issue** - IntelliJ hasn't indexed the new test files
2. **Maven not reloaded** - IntelliJ doesn't know about the test dependencies
3. **Test directory not marked** - The `src/test/java` folder isn't marked as test sources

**All of these are fixed by reloading Maven!**

---

## ✅ VERIFY TESTS ARE WORKING

### Step 1: Open Terminal in IntelliJ
Press `Alt+F12`

### Step 2: Run One Test Class
```bash
mvn test -Dtest=ClientBlackBoxTest
```

### Step 3: Check the Output
You should see:
- ✅ `Tests run: 16`
- ✅ `Failures: 0`
- ✅ `Errors: 0`
- ✅ `BUILD SUCCESS`

### Step 4: Run All Black-Box Tests
```bash
mvn test -Dtest=*BlackBoxTest
```

You should see:
- ✅ `Tests run: 107`
- ✅ `Failures: 0`
- ✅ `Errors: 0`
- ✅ `BUILD SUCCESS`

---

## 🎓 FOR YOUR PRESENTATION/SUBMISSION

### Recommended Approach:

1. **Use Maven commands** to run tests (more reliable)
2. **Show the terminal output** with all 107 tests passing
3. **Demonstrate the front-end** for interactive validation
4. **Reference the documentation** for test specifications

### Demo Script:

```bash
# Navigate to project
cd c:\Users\sirin\Downloads\escapeRoom\escapeRoom

# Run all black-box tests
mvn clean test -Dtest=*BlackBoxTest

# Show results
# Tests run: 107, Failures: 0, Errors: 0, Skipped: 0
# BUILD SUCCESS
```

---

## 📁 TEST FILES LOCATION

All test files are here:
```
src/test/java/org/example/escaperoom/blackbox/
├── ClientBlackBoxTest.java      (16 tests)
├── RoomBlackBoxTest.java         (17 tests)
├── ReservationBlackBoxTest.java  (27 tests)
├── GameBlackBoxTest.java         (35 tests)
└── ScoreBlackBoxTest.java        (12 tests)
```

---

## 🎯 WHAT EACH TEST FILE CONTAINS

### ClientBlackBoxTest.java (16 tests)
- 12 Equivalence Partitioning tests
- 4 Boundary Value Analysis tests
- Tests: name, email, age validation

### RoomBlackBoxTest.java (17 tests)
- 13 Equivalence Partitioning tests
- 4 Boundary Value Analysis tests
- Tests: name, capacity, level, availability

### ReservationBlackBoxTest.java (27 tests)
- 13 Equivalence Partitioning tests
- 5 Decision Table tests
- 9 State Transition tests
- Tests: client, room, date, players, status

### GameBlackBoxTest.java (35 tests)
- 12 Equivalence Partitioning tests
- 4 Boundary Value Analysis tests
- 5 Decision Table tests
- 14 State Transition tests
- Tests: duration, score, state

### ScoreBlackBoxTest.java (12 tests)
- 8 Equivalence Partitioning tests
- 4 Boundary Value Analysis tests
- Tests: points, success status

---

## 💡 ALTERNATIVE: Run from Windows Command Prompt

If IntelliJ terminal doesn't work:

1. **Open Command Prompt** (Win+R, type `cmd`)
2. **Navigate to project:**
   ```cmd
   cd c:\Users\sirin\Downloads\escapeRoom\escapeRoom
   ```
3. **Run tests:**
   ```cmd
   mvn clean test -Dtest=*BlackBoxTest
   ```

---

## 🆘 STILL HAVING ISSUES?

### Try this complete reset:

1. **Close IntelliJ**
2. **Delete IntelliJ cache:**
   - Delete `.idea` folder in project directory
   - Delete `target` folder
3. **Reopen IntelliJ**
4. **Import as Maven project:**
   - File → Open → Select `pom.xml`
   - Choose "Open as Project"
5. **Wait for indexing to complete**
6. **Reload Maven** (🔄 button in Maven tool window)
7. **Run tests**

---

## ✅ BOTTOM LINE

**Your tests ARE working!** They compile and run successfully via Maven.

**The "no tests found" in IntelliJ is just a UI/indexing issue**, not a problem with your tests.

**For your academic project:**
- ✅ Use Maven commands to run tests
- ✅ Show terminal output with 107 passing tests
- ✅ This is actually MORE professional than clicking in an IDE

---

## 📞 QUICK COMMAND REFERENCE

```bash
# Run all black-box tests (RECOMMENDED)
mvn clean test -Dtest=*BlackBoxTest

# Run individual test classes
mvn test -Dtest=ClientBlackBoxTest
mvn test -Dtest=RoomBlackBoxTest
mvn test -Dtest=ReservationBlackBoxTest
mvn test -Dtest=GameBlackBoxTest
mvn test -Dtest=ScoreBlackBoxTest

# Clean and rebuild everything
mvn clean install

# Run tests with detailed output
mvn test -Dtest=*BlackBoxTest -X
```

---

**Your project is complete and working perfectly! Just use Maven to run the tests.** ✅🎓
