package com.example.kinaimailproject.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.kinaimailproject.dto.SendMailDto;

public class SendMail {

	public void sendMail(SendMailDto sendMailDto){

		//メール送信ユーザ
		final String mUser = "funnybunny0504@gmail.com";
        final String mPassword = "kamaisi4";

        Properties props = new Properties();

        //
        props.put("mail.smtp.host", "smtp.gmail.com");// SMTPサーバ名
        props.put("mail.smtp.port", "587"); // SMTPサーバポート
        props.put("mail.smtp.auth", "true");// smtp auth
        props.put("mail.smtp.starttls.enable", "true");// STTLS

        //メールセッションにプロパティをセット
        Session sess = Session.getInstance(props);

        //メッセージオブジェクトの作成
        //MimeMessage mimeMsg = new MimeMessage(sess);


        try {

            final MimeMessage mimeMsg = new MimeMessage(Session.getDefaultInstance(props, new Authenticator() {
                @Override
                // 認証データ。アカウント名とパスワードを指定して下さい。
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mUser, mPassword);
                }
            }));


      mimeMsg.setFrom(new InternetAddress(mUser));//Fromアドレス
      mimeMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(mUser));//送信先アドレス
      mimeMsg.setContent("body", "text/plain; utf-8");
      mimeMsg.setHeader("Content-Transfer-Encoding", "7bit");
      mimeMsg.setSubject("テスト送信");//件名
      //本文
      mimeMsg.setText(sendMailDto.getDate(), "utf-8");//本文

      Transport transport = sess.getTransport("smtp");
      transport.connect(mUser, mPassword);
      transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());// メール送信
      transport.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
