import java.util.*;
import java.io.*;

// ===============================
// PATIENT CLASS
// ===============================

class Patient {

    String patientId;
    String name;
    int severity;
    long arrivalTime;
    String status;

    Patient(String patientId, String name,
            int severity, long arrivalTime) {

        this.patientId = patientId;
        this.name = name;
        this.severity = severity;
        this.arrivalTime = arrivalTime;
        this.status = "WAITING";
    }
}

// ===============================
// CUSTOM LINKED LIST
// ===============================

class PatientLinkedList {

    class Node {

        Patient patient;
        Node next;

        Node(Patient patient) {
            this.patient = patient;
            this.next = null;
        }
    }

    Node head;
    Node tail;

    // Insert patient into linked list
    void addPatient(Patient patient) {

        Node newNode = new Node(patient);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Search patient by ID
    Patient searchPatient(String id) {

        Node temp = head;

        while (temp != null) {

            if (temp.patient.patientId.equalsIgnoreCase(id)) {
                return temp.patient;
            }

            temp = temp.next;
        }

        return null;
    }

    // Display all patient records
    void displayAllPatients() {

        if (head == null) {
            System.out.println("No patient records found.");
            return;
        }

        System.out.println("\n===== ALL PATIENT RECORDS =====");

        Node temp = head;

        while (temp != null) {

            Patient p = temp.patient;

            System.out.println(
                p.patientId + " | " +
                p.name + " | Severity " +
                p.severity + " | " +
                p.status
            );

            temp = temp.next;
        }
    }
}

// ===============================
// MAIN CLASS
// ===============================

public class HospitalEmergencyQueue {

    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "emergency_queue.txt";

    static int patientCounter = 101;

    static long arrivalCounter = 1;

    // Custom Linked List
    static PatientLinkedList patientList =
            new PatientLinkedList();

    // Priority Queue using Heap
    // Higher severity gets higher priority.
    // Earlier arrival time gets priority
    // when severity is equal.

    static PriorityQueue<Patient> waitingQueue =
            new PriorityQueue<>((p1, p2) -> {

                if (p1.severity != p2.severity) {

                    return Integer.compare(
                        p2.severity, p1.severity
                    );
                }

                return Long.compare(
                    p1.arrivalTime, p2.arrivalTime
                );
            });

    // ===============================
    // REGISTER NEW PATIENT
    // ===============================

    static void registerPatient() {

        System.out.println("\n===== REGISTER PATIENT =====");

        System.out.print("Enter Patient Name: ");

        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains("|")) {

            System.out.println("Invalid Patient Name!");

            return;
        }

        System.out.print("Enter Severity (1-5): ");

        if (!sc.hasNextInt()) {

            System.out.println("Invalid Severity!");

            sc.nextLine();

            return;
        }

        int severity = sc.nextInt();

        sc.nextLine();

        // Severity validation

        if (severity < 1 || severity > 5) {

            System.out.println(
                "Invalid Severity! Enter between 1 and 5."
            );

            return;
        }

        // Generate patient ID

        String id = "P" + patientCounter++;

        Patient patient = new Patient(
            id,
            name,
            severity,
            arrivalCounter++
        );

        // Store in Linked List

        patientList.addPatient(patient);

        // Add to Priority Queue

        waitingQueue.add(patient);

        // Required output for Test Case 1

        System.out.println(
            "Patient added: " + id +
            " | " + name +
            " | Severity " + severity
        );
    }

    // ===============================
    // CALL NEXT PATIENT
    // ===============================

    static void callNextPatient() {

        if (waitingQueue.isEmpty()) {

            System.out.println(
                "Queue is empty. No patients waiting."
            );

            return;
        }

        // Remove highest priority patient

        Patient patient = waitingQueue.poll();

        patient.status = "CALLED";

        // Test Case 2:
        // Only one patient is waiting.

        if (waitingQueue.isEmpty()) {

            System.out.println(
                "Calling " + patient.patientId +
                " | " + patient.name +
                " | Severity " + patient.severity
            );
        }

        // Test Case 4:
        // Multiple patients are waiting.

        else {

            System.out.println(
                "Patient with severity " +
                patient.severity +
                " is called first."
            );
        }
    }

    // ===============================
    // SEARCH PATIENT
    // ===============================

    static void searchPatient() {

        System.out.println("\n===== SEARCH PATIENT =====");

        System.out.print("Enter Patient ID: ");

        String id = sc.nextLine().trim();

        Patient patient = patientList.searchPatient(id);

        if (patient == null) {

            // Required output for Test Case 3

            System.out.println(
                "Patient " + id + " not found."
            );

            return;
        }

        System.out.println("\nPatient Found!");

        System.out.println("Patient ID: " + patient.patientId);

        System.out.println("Name: " + patient.name);

        System.out.println("Severity: " + patient.severity);

        System.out.println("Status: " + patient.status);
    }

    // ===============================
    // DISPLAY WAITING QUEUE
    // ===============================

    static void displayQueue() {

        System.out.println("\n===== WAITING PATIENTS =====");

        if (waitingQueue.isEmpty()) {

            System.out.println("No patients waiting.");

            return;
        }

        // Temporary Priority Queue
        // Original queue remains unchanged.

        PriorityQueue<Patient> tempQueue =
                new PriorityQueue<>(waitingQueue);

        System.out.println(
            "Patient ID | Name | Severity"
        );

        while (!tempQueue.isEmpty()) {

            Patient p = tempQueue.poll();

            System.out.println(
                p.patientId + " | " +
                p.name + " | " +
                p.severity
            );
        }
    }

    // ===============================
    // SAVE DATA TO FILE
    // ===============================

    static void saveData() {

        try (
            BufferedWriter writer =
                new BufferedWriter(
                    new FileWriter(FILE_NAME)
                )
        ) {

            PriorityQueue<Patient> tempQueue =
                    new PriorityQueue<>(waitingQueue);

            while (!tempQueue.isEmpty()) {

                Patient p = tempQueue.poll();

                writer.write(
                    p.patientId + "|" +
                    p.name + "|" +
                    p.severity + "|" +
                    p.arrivalTime
                );

                writer.newLine();
            }

            System.out.println(
                "Waiting queue saved successfully."
            );

        } catch (IOException e) {

            System.out.println(
                "Error saving data: " + e.getMessage()
            );
        }
    }

    // ===============================
    // LOAD DATA FROM FILE
    // ===============================

    static void loadData() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            System.out.println(
                "No previous data found. Starting new system."
            );

            return;
        }

        try (
            BufferedReader reader =
                new BufferedReader(
                    new FileReader(file)
                )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {

                    String[] data = line.split("\\|", -1);

                    if (data.length != 4) {
                        continue;
                    }

                    String id = data[0];

                    String name = data[1];

                    int severity = Integer.parseInt(data[2]);

                    long arrivalTime =
                            Long.parseLong(data[3]);

                    // Validate stored records

                    if (!id.matches("P[0-9]+") ||
                        name.trim().isEmpty() ||
                        name.contains("|") ||
                        severity < 1 ||
                        severity > 5 ||
                        arrivalTime < 1) {

                        continue;
                    }

                    Patient patient = new Patient(
                        id,
                        name,
                        severity,
                        arrivalTime
                    );

                    // Restore Linked List

                    patientList.addPatient(patient);

                    // Restore Priority Queue

                    waitingQueue.add(patient);

                    // Update patient ID counter

                    int numericId = Integer.parseInt(
                        id.substring(1)
                    );

                    patientCounter = Math.max(
                        patientCounter,
                        numericId + 1
                    );

                    // Update arrival counter

                    arrivalCounter = Math.max(
                        arrivalCounter,
                        arrivalTime + 1
                    );

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Invalid patient record skipped."
                    );
                }
            }

            System.out.println(
                "Previous waiting patients loaded successfully."
            );

        } catch (IOException e) {

            System.out.println(
                "Error loading data: " + e.getMessage()
            );
        }
    }

    // ===============================
    // MAIN METHOD
    // ===============================

    public static void main(String[] args) {

        // Load previous data at startup

        loadData();

        while (true) {

            System.out.println("\n==================================");

            System.out.println(
                "   HOSPITAL EMERGENCY QUEUE SYSTEM"
            );

            System.out.println("==================================");

            System.out.println("1. Register Patient");

            System.out.println("2. Call Next Patient");

            System.out.println("3. Search Patient");

            System.out.println("4. Display Waiting Queue");

            System.out.println("5. Display All Patient Records");

            System.out.println("6. Save Data");

            System.out.println("7. Exit");

            System.out.print("\nEnter Your Choice: ");

            if (!sc.hasNextInt()) {

                System.out.println(
                    "Please enter a valid number."
                );

                sc.nextLine();

                continue;
            }

            int choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:

                    registerPatient();

                    break;

                case 2:

                    callNextPatient();

                    break;

                case 3:

                    searchPatient();

                    break;

                case 4:

                    displayQueue();

                    break;

                case 5:

                    patientList.displayAllPatients();

                    break;

                case 6:

                    saveData();

                    break;

                case 7:

                    saveData();

                    System.out.println(
                        "Thank you for using Hospital Emergency Queue System!"
                    );

                    sc.close();

                    return;

                default:

                    System.out.println(
                        "Invalid Choice! Please try again."
                    );
            }
        }
    }
}