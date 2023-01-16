/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author 1: Ong Gia Man (s3938231)
  Author 2: Nguyen Le Thu Nhan (s3932151)
  Author 3: Tran Minh Nhat (s3926629)
  Author 4: Nguyen Ngoc Minh Thu (s3941327)
  Date: 01/2023
  Acknowledgement: Acknowledge the resources that you use here.
*/

public class Main {
    public static void main(String[] args) {
        MainCLI session = new MainCLI();
        while (true) {
            session.run();
            System.out.println("Do you want to end the program? (Y/N)");
            if (UserInput.getConfirmation("Y", "N")) {
                System.out.println("Thank you for using!");
                return;
            }
        }
    }
}