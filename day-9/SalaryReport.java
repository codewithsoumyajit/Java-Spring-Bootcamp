import java.text.NumberFormat;
import java.util.Locale;

public class SalaryReport {

    public static void main(String[] args) {

        // --- inputs ---
        int    employeeId  = 101;
        double baseSalary  = 40000.0;
        int    hoursWorked = 180;
        String countryCode = "US";

        // ---------------------------------------------------------------
        // 1. BITWISE OPERATORS
        // ---------------------------------------------------------------

        // Check if employeeId is odd using bitwise AND
        boolean isOddId = (employeeId & 1) == 1;

        // Determine tax bracket using right shift (hoursWorked / 8), cap at 2
        byte taxBracket = (byte) Math.min(hoursWorked >> 3, 2);

        // Build permission flags using bitwise OR
        // Bit 0 (value 1) = overtime allowed  → if hoursWorked > 160
        // Bit 1 (value 2) = bonus eligible    → if employeeId is odd
        int permissions = 0;
        if (hoursWorked > 160) permissions |= 1;  // set bit 0
        if (isOddId)           permissions |= 2;  // set bit 1

        // ---------------------------------------------------------------
        // 2. ARITHMETIC OPERATORS
        // ---------------------------------------------------------------

        // Hourly rate derived from monthly base (assuming 160 working hours/month)
        double hourlyRate = baseSalary / 160.0;

        // Gross salary = base + overtime pay (1.5x rate for all hours worked)
        double grossSalary = baseSalary + (hoursWorked * 1.5 * hourlyRate);

        // Tax amount based on bracket
        double taxRate;
        if (taxBracket == 0)      taxRate = 0.00;
        else if (taxBracket == 1) taxRate = 0.10;
        else                      taxRate = 0.20;

        double taxAmount = grossSalary * taxRate;

        // Bonus for odd employee IDs (amount depends on locale)
        double bonus = 0.0;
        if (isOddId) {
            if      (countryCode.equals("US")) bonus = 10.0;
            else if (countryCode.equals("IN")) bonus = 500.0;
            else if (countryCode.equals("DE")) bonus = 8.0;
        }

        // Net salary
        double netSalary = grossSalary - taxAmount + bonus;

        // ---------------------------------------------------------------
        // 3. DATA TYPES — summary of types used:
        //    int     → employeeId, hoursWorked, permissions
        //    double  → baseSalary, hourlyRate, grossSalary, taxAmount, bonus, netSalary
        //    boolean → isOddId
        //    byte    → taxBracket
        // ---------------------------------------------------------------

        // ---------------------------------------------------------------
        // 4. LOCALE — format currency based on countryCode
        // ---------------------------------------------------------------

        Locale locale;
        switch (countryCode) {
            case "IN": locale = new Locale("en", "IN"); break;
            case "DE": locale = Locale.GERMANY;         break;
            default:   locale = Locale.US;              break;
        }

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        // ---------------------------------------------------------------
        // OUTPUT
        // ---------------------------------------------------------------

        System.out.println("===== Employee Salary Report =====");
        System.out.println("Employee ID  : " + employeeId);
        System.out.println("Hours Worked : " + hoursWorked);
        System.out.println("Odd ID?      : " + isOddId);
        System.out.println("Tax Bracket  : " + taxBracket);
        System.out.println("Permissions  : " + String.format("%2s",
                Integer.toBinaryString(permissions)).replace(' ', '0'));
        System.out.println("Gross Salary : " + fmt.format(grossSalary));
        System.out.println("Tax Deducted : " + fmt.format(taxAmount));
        System.out.println("Bonus        : " + fmt.format(bonus));
        System.out.println("Net Salary   : " + fmt.format(netSalary));
        System.out.println("==================================");
    }
}