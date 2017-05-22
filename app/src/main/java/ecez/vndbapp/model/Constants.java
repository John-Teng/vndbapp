package ecez.vndbapp.model;

/**
 * Created by johnteng on 2017-04-23.
 */

public class Constants {

    public static final String serverErrorResponse = "Error response from the server";
    public static final String serverNotLoggedInError = "Not logged into server";
    public static final String jsonSerializationError = "Problem occurred with json serialization";
    public static final String noGenres = "No Genre Information Available";

    public static String getDate (String date) {
        StringBuilder a = new StringBuilder();
        //1234-12-12
        String month = date.substring(5,6);
        switch (month) {
            case "01":
                a.append("January");
                break;
            case "02":
                a.append("February");
                break;
            case "03":
                a.append("March");
                break;
            case "04":
                a.append("April");
                break;
            case "05":
                a.append("May");
                break;
            case "06":
                a.append("June");
                break;
            case "07":
                a.append("July");
                break;
            case "08":
                a.append("August");
                break;
            case "09":
                a.append("September");
                break;
            case "10":
                a.append("October");
                break;
            case "11":
                a.append("November");
                break;
            case "12":
                a.append("December");
                break;
        }
        a.append(date.substring())

}
