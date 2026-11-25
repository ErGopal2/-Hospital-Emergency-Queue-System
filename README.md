# 🏥 Hospital Emergency Queue System (`PS No. 18`)

> **When seconds matter in the ER, urgency comes first—without sacrificing fairness or losing a single patient record.**

A sharp, console-driven **Data Structures and Algorithms (DSA)** application built in **Java** for the **School of Computer Science and Engineering, Rungta International Skills University**. In an emergency room, a traditional First-In, First-Out (FIFO) queue treats a minor case arriving earlier ahead of a critical emergency arriving moments later. This system solves that problem with a **Priority Queue (Binary Heap)** ranked by severity (**1 to 5**, where **5** is highest urgency) and **arrival time** as a tie-breaker so patients with equal severity are called in fair arrival order.

---

## ✨ What Makes It Click
* **Clinical Priority + Fair Tie-Breaking:** High-severity cases (`5` down to `1`) take immediate precedence, while equal-severity patients are ordered by earliest arrival (`arrivalTime`).
* **Two Data Structures, One Cohesive Flow:** Combines a `PriorityQueue` heap for rapid triage dispatch with a custom singly linked list (`PatientLinkedList`) for storing patient records, searching by ID, and displaying records.
* **Resilient File Persistence:** Automatically loads valid waiting patients from `emergency_queue.txt` on startup and writes remaining waiting patients back on save or exit.
* **Defensive Input Validation:** Enforces severity bounds (`1–5`), rejects invalid names or menu choices, and safely handles empty queues.

---

## 🧩 Architecture at a Glance

| Component | Role in the System |
| :--- | :--- |
| **`Patient`** | Holds `patientId` (`P101`, `P102`, ...), `name`, `severity` (`1–5`), `arrivalTime`, and `status` (`WAITING` / `CALLED`) |
| **`PatientLinkedList`** | Custom singly linked list (`Node`, `head`, `tail`) supporting record insertion, ID search, and displaying patient records |
| **`HospitalEmergencyQueue`** | Runs the menu loop, custom priority comparator, input/queue validation, non-destructive queue views, and `emergency_queue.txt` file I/O |

---

## ⚙️ Core Capabilities
* **Register Patient:** Validates name and severity (`1–5`), assigns a Patient ID and arrival sequence, and adds the patient to both the linked list and priority queue.
* **Call Next Patient:** Checks for an empty queue first; otherwise calls the highest-priority patient and marks their status as `CALLED`.
* **Search Patient:** Searches the linked list by Patient ID and displays full details—or reports cleanly when not found (such as `P999`).
* **Display Queue & Records:** Prints waiting patients in priority order without mutating the live queue, alongside full patient records.
* **Startup Load & Exit Save:** Restores waiting patients from `emergency_queue.txt` at launch and saves waiting queue data on save or exit.

---

## ✅ Test Case Highlights (`PS No. 18`)
* **Normal Registration:** Registering `Riya` with severity `4` adds `P101` and outputs `Patient added: P101 | Riya | Severity 4`.
* **Single-Patient Call Next:** Calling next when only `P101` is waiting outputs `Calling P101 | Riya | Severity 4`.
* **Missing ID Lookup:** Searching for `P999` outputs `Patient P999 not found.`.
* **Multi-Severity Triage:** When waiting patients have severities `2`, `5`, and `3`, calling next prioritizes severity `5` first[cite: 1].

---

## ⚡ Quick Start
```bash
javac HospitalEmergencyQueue.java
java HospitalEmergencyQueue
```[cite: 1, 12]

**Project Synopsis :-** https://drive.google.com/file/d/1dakd1bPHtygy67IeNUdVmpVZBoqdVekX/view?usp=sharing
**Project PPT :- ** https://docs.google.com/presentation/d/1TjTkd6s-03F5Y0ZL679kncgZHUYeP7Yc/edit?usp=sharing&ouid=111827267732138836078&rtpof=true&sd=true

**Gopal Krishna Shukla** (`RU-25-10528`)  
B.Tech CSE (AI & ML) — **Rungta International Skills University**[cite: 12, 32]
