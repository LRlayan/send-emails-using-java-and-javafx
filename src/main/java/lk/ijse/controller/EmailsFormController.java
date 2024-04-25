package lk.ijse.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailsFormController {
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXTextArea txtMsgArea;

    @FXML
    void sendBtnOnAction(ActionEvent event) throws MessagingException {
        String recipientEmail = txtEmail.getText();
        sendEmail(recipientEmail);
    }

    private void sendEmail(String recipientEmail) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "rameshlayan4@gmail.com";
        String password = "uugu cngg vknw jmiu";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });

        Message message = prepareMessage(session,myEmail,recipientEmail,txtMsgArea.getText());
            if (message!=null){
                new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Please Try Again").show();
            }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipientEmail, String msg) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(recipientEmail)
            });

            message.setSubject("Messages");
            message.setText(msg);

            return message;
        }catch (Exception e){
            Logger.getLogger(EmailsFormController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
}