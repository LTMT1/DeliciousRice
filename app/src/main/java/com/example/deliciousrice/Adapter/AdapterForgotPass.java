package com.example.deliciousrice.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Activity.ConfirmOTPActivity;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class AdapterForgotPass extends RecyclerView.Adapter<AdapterForgotPass.ForgotPassHolder> {
    private Random randomcode = new Random();
    private static int otp;
    Context context;
    ArrayList<Customer> CustomerArrayList;
    View view;
    private static String mess;

    public AdapterForgotPass(Context context, ArrayList<Customer> CustomerArrayList) {
        this.context = context;
        this.CustomerArrayList = CustomerArrayList;
    }

    @NonNull
    @Override
    public ForgotPassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tai_khoan, parent, false);
        return new ForgotPassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForgotPass.ForgotPassHolder holder, @SuppressLint("RecyclerView") int position) {
        Customer customer = CustomerArrayList.get(position);
        Glide.with(context)
                .load(customer.getImage())
                .into(holder.imgCustomer);
        holder.tvusername.setText(customer.getUser_name());
        otp = randomcode.nextInt((999999 - 100000) + 100000);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("X??c nh???n t??i kho???n.");
                builder.setMessage("Ch??ng t??i s??? g???i cho b???n m?? ????? x??c nh???n t??i kho???n n??y l?? c???a b???n.");
                builder.setPositiveButton("G???i", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mess = "Xin ch??o " + customer.getUser_name() + ", \n\nCh??ng t??i ???? nh???n ???????c y??u c???u ?????t l???i m???t kh???u c???a b???n. \n M?? kh??i ph???c m???t kh???u c???a b???n b??n d?????i:" + otp + "\n\nDeliciousRice ch??n th??nh c???m ??n.";
                        buttonSendEmail(customer.getEmail());
                        Intent intent = new Intent(v.getContext(), ConfirmOTPActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("data", customer);
                        v.getContext().startActivity(intent);
                    }
                });
                builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return CustomerArrayList.size();
    }

    public class ForgotPassHolder extends RecyclerView.ViewHolder {
        private ImageView imgCustomer;
        private TextView tvusername;
        private ConstraintLayout constraintLayout;

        public ForgotPassHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.contranitCustomer);
            imgCustomer = (ImageView) itemView.findViewById(R.id.ImgCustomer);
            tvusername = (TextView) itemView.findViewById(R.id.tvUsernameCustomer);
        }
    }

    public static void buttonSendEmail(String email) {

        try {
            String stringSenderEmail = "deliciousrices@gmail.com";
            String stringPasswordSenderEmail = "jzqnirrpudyvquku";
            String stringHost = "smtp.gmail.com";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            mimeMessage.setSubject("M?? x??c minh email: " + otp);
            mimeMessage.setText(mess);
            mimeMessage.setFrom("DeliciousRice");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}