interface StatisticCLIIndex {
    String TOTAL_REVENUE = "0";
    String REVENUE_BY_DATE = "1";
    String PRODUCTS_BY_SALES = "2";
    String CUSTOMERS_BY_SPENDING = "3";
    String LIST_MEMBERSHIPS = "4";
}

public class StatisticCLI implements CLI, StatisticCLIIndex {

    public boolean run() {
        System.out.println("No permission");
        return false;
    }

    public boolean run(Admin admin) {
        if (admin != null) {
            boolean state = true;
            while (state) {
                UserInput.clearConsole();
                System.out.println("STATISTICS: ");
                Utilities.printStringBullet(getOptions());
                System.out.println("Enter an option: ");
                String command = UserInput.getInput();
                state = executeCommand(command, admin);
                System.out.println("Continue? (Y/N): ");
                if (UserInput.getConfirmation("Y", "N")) {
                    state = true;
                }
            }
        }

        return false;
    }

    public boolean executeCommand(String command, Admin admin) {
        if (command == null || admin == null) {
            return false;
        }
        switch (command) {
            case TOTAL_REVENUE:
                System.out.printf("Total revenue = %.2f\n", admin.getTotalRevenue());
                break;
            case REVENUE_BY_DATE:
                System.out.println("Enter date (" + DateFormat.dateFormat + ")");
                String date = UserInput.getInput();
                System.out.printf("Revenue on day %s = %.2f\n", date, admin.getRevenueOnDay(date));
                break;
            case PRODUCTS_BY_SALES:
                System.out.println("Sort (DESC/ASC)");
                admin.sortProductBySales(UserInput.getConfirmation("DESC", "ASC"));
                break;
            case CUSTOMERS_BY_SPENDING:
                System.out.println("Sort (DESC/ASC)");
                admin.sortCustomerBySpending(UserInput.getConfirmation("DESC", "ASC"));
                break;
            case LIST_MEMBERSHIPS:
                admin.numberOfMembershipType();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        return false;

    }

    @Override
    public String[] getOptions() {
        String[] options = {
                "Calculate Total Revenue",
                "Calculate Revenue by Date",
                "List Products by Sales",
                "List Customers by Spending",
                "List Memberships"
        };
        return options;
    }

}