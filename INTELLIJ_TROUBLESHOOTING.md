# 🔧 INTELLIJ TROUBLESHOOTING GUIDE

## ❌ Problem: "No tests were found" in IntelliJ

This is a common issue when IntelliJ doesn't recognize JUnit 5 tests. Here are the solutions:

---

## ✅ SOLUTION 1: Reload Maven Project (RECOMMENDED)

1. **Open Maven Tool Window:**
   - Click on `View` → `Tool Windows` → `Maven`
   - OR press `Ctrl+Shift+A` and type "Maven"

2. **Reload Project:**
   - In the Maven window, click the **Reload** button (🔄 circular arrow icon)
   - Wait for IntelliJ to re-import the project

3. **Run Tests:**
   - Right-click on `ClientBlackBoxTest.java`
   - Select `Run 'ClientBlackBoxTest'`

---

## ✅ SOLUTION 2: Mark Test Directory

1. **Right-click on the test folder:**
   - Navigate to: `src/test/java`
   - Right-click on the `java` folder

2. **Mark Directory as:**
   - Select `Mark Directory as` → `Test Sources Root`
   - The folder should turn **green**

3. **Run Tests:**
   - Right-click on `ClientBlackBoxTest.java`
   - Select `Run 'ClientBlackBoxTest'`

---

## ✅ SOLUTION 3: Invalidate Caches

1. **Invalidate Caches:**
   - Click `File` → `Invalidate Caches...`
   - Check **all** boxes
   - Click `Invalidate and Restart`

2. **Wait for indexing:**
   - Let IntelliJ finish indexing (bottom right corner)

3. **Run Tests:**
   - Right-click on `ClientBlackBoxTest.java`
   - Select `Run 'ClientBlackBoxTest'`

---

## ✅ SOLUTION 4: Configure JUnit 5 in IntelliJ

1. **Open Settings:**
   - Press `Ctrl+Alt+S` (Windows/Linux) or `Cmd+,` (Mac)

2. **Navigate to Build Tools:**
   - Go to: `Build, Execution, Deployment` → `Build Tools` → `Maven` → `Runner`

3. **Delegate to Maven:**
   - Check: ✅ `Delegate IDE build/run actions to Maven`
   - Click `Apply` and `OK`

4. **Run Tests:**
   - Right-click on `ClientBlackBoxTest.java`
   - Select `Run 'ClientBlackBoxTest'`

---

## ✅ SOLUTION 5: Run from Maven Command Line

If IntelliJ still doesn't work, use Maven directly:

### Option A: Run All Tests
```bash
mvn clean test
```

### Option B: Run Specific Test Class
```bash
mvn test -Dtest=ClientBlackBoxTest
mvn test -Dtest=RoomBlackBoxTest
mvn test -Dtest=ReservationBlackBoxTest
mvn test -Dtest=GameBlackBoxTest
mvn test -Dtest=ScoreBlackBoxTest
```

### Option C: Run All Black-Box Tests
```bash
mvn test -Dtest=*BlackBoxTest
```

---

## ✅ SOLUTION 6: Check JUnit Version in pom.xml

Make sure your `pom.xml` has the correct JUnit dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

This is already included in your `pom.xml` ✅

---

## 🎯 QUICK FIX: Run from Terminal in IntelliJ

1. **Open Terminal in IntelliJ:**
   - Click `View` → `Tool Windows` → `Terminal`
   - OR press `Alt+F12`

2. **Navigate to project directory:**
   ```bash
   cd c:\Users\sirin\Downloads\escapeRoom\escapeRoom
   ```

3. **Run tests:**
   ```bash
   mvn clean test
   ```

4. **View results:**
   - Tests will run in the terminal
   - Results will be displayed with ✅ or ❌

---

## 📊 Expected Output

When tests run successfully, you should see:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.example.escaperoom.blackbox.ClientBlackBoxTest
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running org.example.escaperoom.blackbox.RoomBlackBoxTest
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running org.example.escaperoom.blackbox.ReservationBlackBoxTest
[INFO] Tests run: 27, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running org.example.escaperoom.blackbox.GameBlackBoxTest
[INFO] Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running org.example.escaperoom.blackbox.ScoreBlackBoxTest
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 107, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
```

---

## 🔍 Verify Test Discovery

To check if IntelliJ can see your tests:

1. **Open test class** (`ClientBlackBoxTest.java`)
2. **Look for green play icons** (▶️) next to:
   - The class name
   - Each `@Test` method
3. **If you see the icons:**
   - Click any icon to run that specific test
4. **If you DON'T see the icons:**
   - Try Solution 1 (Reload Maven Project)

---

## 🆘 Still Not Working?

### Check Project SDK:

1. **Open Project Structure:**
   - Press `Ctrl+Alt+Shift+S`
   - OR `File` → `Project Structure`

2. **Verify SDK:**
   - Go to `Project` tab
   - Make sure SDK is set to **Java 17** or higher
   - Make sure Language Level is **17**

3. **Verify Modules:**
   - Go to `Modules` tab
   - Make sure `src/test/java` is marked as **Test Sources** (green)

---

## 💡 Alternative: Run Individual Test Method

If you want to run just one test:

1. **Open the test class**
2. **Click the green play icon** (▶️) next to a specific `@Test` method
3. **Select** `Run 'testValidClientCreation()'`

---

## 📝 Notes

- ✅ All tests are **JUnit 5** compatible
- ✅ All tests are in the correct package: `org.example.escaperoom.blackbox`
- ✅ All tests have `@Test` annotations
- ✅ Maven can compile and run the tests successfully
- ✅ The issue is likely IntelliJ configuration, not the tests themselves

---

## 🎯 RECOMMENDED APPROACH

**For your presentation/submission, use Maven:**

```bash
# Run all tests and generate report
mvn clean test

# Run specific service tests
mvn test -Dtest=ClientBlackBoxTest
```

This ensures:
- ✅ Tests run consistently
- ✅ Results are reproducible
- ✅ No IDE-specific issues
- ✅ Professional demonstration

---

## 📞 Quick Reference Commands

```bash
# Clean and compile
mvn clean compile

# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=ClientBlackBoxTest

# Run all black-box tests
mvn test -Dtest=*BlackBoxTest

# Skip tests (if needed)
mvn clean install -DskipTests
```

---

**Try Solution 1 first (Reload Maven Project) - it fixes 90% of cases!** 🎯
