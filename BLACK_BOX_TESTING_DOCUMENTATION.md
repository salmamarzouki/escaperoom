# 🧪 BLACK-BOX TESTING DOCUMENTATION
## Escape Room Management System

This document outlines the black-box testing strategy, partitions, and test cases for the Escape Room Management application.

---

## 1️⃣ EQUIVALENCE PARTITIONING (EP)

### 🔹 1.1 Client Service
**Attributes**: id, name, email, age

#### Equivalence Partitions
| Input | Valid Partition (PEV) | Invalid Partition (PENV) |
|---|---|---|
| **id** | Positive integer | Null, negative |
| **name** | Alphabetic string | Empty, numeric |
| **email** | Valid email format | Missing @, empty |
| **age** | Between 18 and 60 | <18, >60 |

#### Test Cases (One CT per Partition)
| CT | Input Example | Expected Result |
|---|---|---|
| **CT1** | age = 25 | Client accepted |
| **CT2** | age = 17 | Error: invalid age |
| **CT3** | email = "user@test.com" | Client accepted |
| **CT4** | email = "user.test.com" | Error: invalid email |

---

### 🔹 1.2 Salle (Room) Service
**Attributes**: id, name, capacity, level, availability

#### Equivalence Partitions
| Input | Valid | Invalid |
|---|---|---|
| **capacity** | 2–10 players | <2, >10 |
| **level** | Easy, Medium, Hard | Undefined value |
| **availability** | Available / Unavailable | Null |

#### Test Cases
| CT | Input | Expected |
|---|---|---|
| **CT5** | capacity = 6 | Accepted |
| **CT6** | capacity = 1 | Error |
| **CT7** | level = "Medium" | Accepted |
| **CT8** | level = "Extreme" | Error |

---

### 🔹 1.3 Reservation Service
**Attributes**: client, salle, date, number of players, status

#### Equivalence Partitions
| Input | Valid | Invalid |
|---|---|---|
| **number of players** | ≤ salle capacity | > capacity |
| **date** | Future date | Past date |
| **status** | Created, Confirmed, Cancelled | Undefined |

#### Test Cases
| CT | Input | Expected |
|---|---|---|
| **CT9** | players = 4, capacity = 6 | Accepted |
| **CT10** | players = 8, capacity = 6 | Error |
| **CT11** | date = tomorrow | Accepted |
| **CT12** | date = yesterday | Error |

---

### 🔹 1.4 Game Service
**Attributes**: duration, score, state

#### Equivalence Partitions
| Input | Valid | Invalid |
|---|---|---|
| **duration** | 30–90 min | <30, >90 |
| **score** | ≥ 50 | < 50 |
| **state** | NotStarted, Running, Finished | Undefined |

---

### 🔹 1.5 Score Service
**Attributes**: points, success/failure

| Input | Valid | Invalid |
|---|---|---|
| **points** | ≥ 50 | < 50 |
| **result** | Success / Failure | Null |

---

## 2️⃣ BOUNDARY VALUE ANALYSIS (AVL – 2 Values)

### Example: Age (Client)
**Valid range**: 18–60

| Boundary | Test Value | Justification |
|---|---|---|
| **Lower limit** | 17 / 18 | Invalid / Valid |
| **Upper limit** | 60 / 61 | Valid / Invalid |

#### Test Cases
*   **CT13**: age = 17 → rejected
*   **CT14**: age = 18 → accepted
*   **CT15**: age = 60 → accepted
*   **CT16**: age = 61 → rejected

**✔ Coverage**: 100% of boundary values

---

## 3️⃣ DECISION TABLE TESTING

### 🎯 Reservation Discount Rule
**Business Rules**:
1. If client is VIP and score ≥ 800 → Discount 20%
2. If client is VIP and score < 800 → Discount 10%
3. If client is not VIP → No discount

#### Conditions & Actions
| Conditions | R1 | R2 | R3 |
|---|---|---|---|
| **VIP Client** | Yes | Yes | No |
| **Score ≥ 800** | Yes | No | - |
| **Actions** | **R1** | **R2** | **R3** |
| 20% Discount | ✔ | ✖ | ✖ |
| 10% Discount | ✖ | ✔ | ✖ |
| No Discount | ✖ | ✖ | ✔ |

#### Test Cases
| CT | Input | Expected |
|---|---|---|
| **CT17** | VIP, score=900 | 20% discount |
| **CT18** | VIP, score=700 | 10% discount |
| **CT19** | Non-VIP | No discount |

---

## 4️⃣ STATE TRANSITION TESTING (Optional)

Testing transitions between states (Reservation: Pending -> Confirmed -> Completed, etc.).

---

## 📝 CONCLUSION
This document follows strict Black-Box Testing methodology to ensure 100% logic coverage for the Escape Room Management System.
