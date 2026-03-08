# MO-IT101-Group50: MotorPH Basic Payroll System

**Course:** MO-IT101 – Computer Programming 1
**Institution:** Mapúa Malayan Digital College (MMDC)

---

## 🤝 Group Details

| Name | Contribution |
|------|-------------|
| Angeline Latoja | Repository Host, Java program development, payroll computation logic, file reading logic, deduction methods, GitHub setup |
| Precious Angela Moudebelu | Project plan revision and documentation |
| Raneza Lyra Robles | Team member – pending contribution |
| Nestor Ilano | Team member – pending contribution |

---

## 📄 Program Details

The MotorPH Basic Payroll System is a Java console application that automates payroll computation for all MotorPH employees from June to December 2024.

How the system works:
1. Reads employee records (ID, Name, Birthday, Hourly Rate) from employee_data.csv
2. Reads attendance records (login/logout per day) from attendance_data.csv
3. Calculates total hours worked per semi-monthly cutoff:
   - Only hours between 8:00 AM and 5:00 PM are counted (no overtime)
   - Grace period: login at or before 8:10 AM is treated as exactly 8:00 AM
   - 1 hour lunch break is deducted per working day
   - 1st cutoff = days 1 to 15 | 2nd cutoff = days 16 to end of month
4. Computes gross pay per cutoff: Hours Worked x Hourly Rate
5. Adds 1st and 2nd cutoff gross pay to get monthly gross for deduction bracket lookup
6. Computes government deductions: SSS, PhilHealth, Pag-IBIG, and Withholding Tax
7. Deductions are applied to the 2nd cutoff only
8. Net Pay = 2nd Cutoff Gross - Total Deductions
9. Displays complete payroll summary for all 34 employees, June to December 2024

---

## 📅 Project Plan Link

[Click here to view our Project Plan](https://docs.google.com/spreadsheets/d/1CX7zEg97ul5RsXzk7KvQqvoTBxVL9p0AlwIeztWFg1w/edit?usp=drivesdk)

---

## 📁 Files in this Repository

| File | Description |
|------|-------------|
| MotorPH_PayrollSystem.java | Main Java program (single file) |
| employee_data.csv | Employee records (ID, Name, Birthday, Salary, Hourly Rate) |
| attendance_data.csv | Attendance records June to December 2024 |
| README.md | This file |

---

## ▶️ How to Run

1. Clone or download this repository
2. Place all files in the same folder
3. Open MotorPH_PayrollSystem.java in NetBeans
4. Right-click the file and click Run File (Shift+F6)

---

*MotorPH Payroll System — MO-IT101 Group 50, MMDC 2024*
