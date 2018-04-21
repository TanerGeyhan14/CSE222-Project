package bootstrappers.database;

import bootstrappers.Address;
import bootstrappers.Home;
import bootstrappers.usergroups.Member;
import bootstrappers.usergroups.SystemUserAbstract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {

    private static String userList;

    private static String homeList;

    private static String delimiter;

    private static String separator;

    public FileOperations()
    {
        userList = "src/userList.csv";
        homeList = "src/roomList.csv";
        delimiter = ",";
        separator = "\n";
    }

    public String getUserList(){return userList;}


    public String getHomeList(){return homeList;}


    public void writeUserList(ArrayList<SystemUserAbstract> userList){
        String fileHeader = "User Type, Name, Surname, Phone Number, E-mail, Password";
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(getUserList(), false);
            fileWriter.append(fileHeader);
            fileWriter.append(separator);

            for (SystemUserAbstract user : userList)
            {
                fileWriter.write(user.getUserType());
                fileWriter.write(delimiter);
                fileWriter.write(user.getName());
                fileWriter.write(delimiter);
                fileWriter.write(user.getSurname());
                fileWriter.write(delimiter);
                fileWriter.write(user.getPhoneNumber());
                fileWriter.write(delimiter);
                fileWriter.write(user.getEmail());
                fileWriter.write(delimiter);
                fileWriter.write(user.getPassword());
                fileWriter.write(separator);
            }
        }
        catch(Exception e){
            System.err.println("Error: User list was not written in the file.");
            e.printStackTrace();
        }
        finally{
            try{
                fileWriter.close();
            }
            catch(IOException e){
                System.err.println("Error: 'userList.csv' file could not close.");
                e.printStackTrace();
            }
        }
    }


    public void writeHomeList(ArrayList<Home> homeList){
        String fileHeader = "Ad No, Province, Country, Neighborhood, Status, Type, Count Of Room, Owner's Contact Email";
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(getHomeList(), false);
            fileWriter.append(fileHeader);
            fileWriter.append(separator);

            for (Home home : homeList)
            {
                fileWriter.write(String.valueOf(home.getAdNumber()));
                fileWriter.write(delimiter);
                fileWriter.write(home.getAddress().getProvince());
                fileWriter.write(delimiter);
                fileWriter.write(home.getAddress().getCountry());
                fileWriter.write(delimiter);
                fileWriter.write(home.getAddress().getNeighborhood());
                fileWriter.write(delimiter);
                fileWriter.write(home.getStatus());
                fileWriter.write(delimiter);
                fileWriter.write(home.getType());
                fileWriter.write(delimiter);
                fileWriter.write(home.getCountOfRoom());
                fileWriter.write(delimiter);
                fileWriter.write(home.getOwner().getEmail());
                fileWriter.write(separator);
                fileWriter.write(home.getArea());
                fileWriter.write(separator);
            }
        }
        catch(Exception e){
            System.err.println("Error: Home list was not written in the file.");
            e.printStackTrace();
        }
        finally{
            try{
                fileWriter.close();
            }
            catch(IOException e){
                System.err.println("Error: 'homeList.csv' file could not close.");
                e.printStackTrace();
            }
        }
    }


    public void readUserList(ArrayList<SystemUserAbstract> userList)
    {
        int type = 0,  name = 1, surname = 2, phoneNumber = 3, email = 4, password = 5;
        BufferedReader fileReader = null;

        try{
            fileReader = new BufferedReader(new FileReader(getUserList()));
            String line;
            fileReader.readLine();
            while((line=fileReader.readLine()) != null)
            {
                String[] tokens = line.split(delimiter);
                SystemUserAbstract user = new Member(tokens[name], tokens[surname], tokens[password], tokens[phoneNumber],
                        tokens[email], tokens[type]);
            }
        }
        catch(Exception e){
            System.err.println("Error: 'userList.csv' was not read in file.");
            e.printStackTrace();
        }
        finally{
            try{
                fileReader.close();
            }
            catch(IOException e){
                System.err.println("Error: 'userList' file could not close.");
                e.printStackTrace();
            }
        }
    }


    public void readHomeList(ArrayList<Home> homeList, ArrayList<SystemUserAbstract> userList){

        int adNo = 0,  province = 1, country = 2, neighborhood = 3, status = 4, type = 5, countOfRoom = 6, contactEmail = 7, area = 8;
        BufferedReader fileReader = null;
        try{
            fileReader = new BufferedReader(new FileReader(getHomeList()));
            String line;
            fileReader.readLine();
            while((line=fileReader.readLine()) != null)
            {
                String[] tokens = line.split(delimiter);
                Address address = new Address(tokens[province], tokens[country], tokens[neighborhood]);

                Member user = null;
                for (SystemUserAbstract target : userList) {
                    if (target.getEmail().equals(tokens[contactEmail]))
                        user = (Member)target;
                }
                Home home = new Home(tokens[countOfRoom], tokens[status], tokens[type], address, Integer.parseInt(tokens[adNo]), user, Integer.parseInt(tokens[area]));
                homeList.add(home);
            }

        }
        catch(Exception e){
            System.err.println("Error: 'homeList.csv' was not read in file.");
            e.printStackTrace();
        }
        finally{
            try{
                fileReader.close();
            }
            catch(IOException e){
                System.err.println("Error: 'homeList' file could not close.");
                e.printStackTrace();
            }
        }
    }
}