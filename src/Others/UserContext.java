/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Others;

/**
 *
 * @author RaeYobz
 */
public class UserContext {
    private static UserContext instance;
    private Integer userId;

    // Private constructor to prevent instantiation
    private UserContext() {}

    // Synchronized to ensure thread-safety if accessed by multiple threads
    public static synchronized UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}