### Problem Statement: Student Grade & Performance Reporter

#### **Problem Description**

You are tasked with building a performance evaluation module for an educational institution. Given a student's name, the number of subjects $N$, and their respective names and marks (out of 100), process the data to output the individual subject grades, the overall percentage, the overall grade, and the final result status.

Additionally, output any specific warning or critical failure logs based on thresholds.

---

#### **Input Format**

1. The first line contains a string $S$ — the student's name.
2. The second line contains an integer $N$ — the number of subjects.
3. The next $N$ blocks of input each contain:
* A string $Sub_i$ — the subject name.
* A floating-point number $M_i$ ($0.0 \le M_i \le 100.0$) — the marks scored in subject $i$.



---

#### **Output Format**

1. A formatted report displaying:
* Student's Name.
* For each subject: Subject name, Marks (rounded to 1 decimal place), and Subject Grade.
* Total Average (Overall Percentage rounded to 1 decimal place) and Overall Grade.
* Overall Status: `"PASS"` if overall percentage $\ge 40.0$, otherwise `"FAIL"`.


2. A separate warning log listing:
* A warning for any subject where $M_i < 40.0$.
* A critical error if the overall percentage $< 40.0$.



---

#### **Grading Scale Rules**

| Percentage / Marks Range | Grade |
| --- | --- |
| $[90.0, 100.0]$ | `O` |
| $[80.0, 90.0)$ | `A+` |
| $[70.0, 80.0)$ | `A` |
| $[60.0, 70.0)$ | `B+` |
| $[50.0, 60.0)$ | `B` |
| $[40.0, 50.0)$ | `C` |
| $[0.0, 40.0)$ | `F` |

---

#### **Constraints**

* $1 \le N \le 10$
* $1 \le \vert{}S\vert{}, \vert{}Sub_i\vert{} \le 50$
* $0.0 \le M_i \le 100.0$

---

#### **Sample Input**

```text
Alice Smith
3
Mathematics
92.5
Physics
38.0
Chemistry
65.0

```

#### **Sample Output**

```text
============================================
| RESULT CARD — Alice Smith                 |
============================================
| Subject                 Marks    Grade    |
| ----------------------------------------  |
| Mathematics              92.5        O    |
| Physics                  38.0        F    |
| Chemistry                65.0       B+    |
| ----------------------------------------  |
| TOTAL AVERAGE            65.2       B+    |
| STATUS: PASS                              |
============================================

WARNING: Failed in Physics (38.0/100)

```

---