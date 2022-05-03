package com.example.chatappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Helpers.MyPair;
import Helpers.MyStringPair;
import Model.Chat;
import Model.User;

public class PieActivity extends AppCompatActivity {

    AnyChartView anyChartView;
    DatabaseReference chat_reference;
    DatabaseReference user_reference;
    FirebaseUser fuser;
    MyPair[] contactAndChat = new MyPair[10000];
    MyStringPair[] contactAndId = new MyStringPair[10000];
    private int contactAndChatSize = 0;
    private int contactAndIdSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChat();
    }

    private void setupPieChat() {
        final Pie pie = AnyChart.pie();
        final List<DataEntry> dataEntries = new ArrayList<>();
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        chat_reference  = FirebaseDatabase.getInstance().getReference("Chats");
        user_reference = FirebaseDatabase.getInstance().getReference("Users");

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (contactAndIdSize != 0) {
                        Boolean error=false;
                        for (int i = 0; i < contactAndIdSize; i++) {
                            if (user.getId().equals(contactAndId[i].getId())) {
                                error = true;
                            }
                        }
                        if(!error){
                            contactAndId[contactAndIdSize] = new MyStringPair(user.getUsername(), user.getId());
                            contactAndIdSize++;
                        }
                    } else {
                        contactAndId[contactAndIdSize] = new MyStringPair(user.getUsername(), user.getId());
                        contactAndIdSize++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chat_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getSender().equals(fuser.getUid())) {
                        if (contactAndChatSize == 0) {
                            //adaugarea primului mesaj cu persoana respectiva
                            String username = new String();
                            //search in contact and id for username
                            Boolean found = false;
                            for (int i = 0; i < contactAndIdSize; i++) {
                                if (chat.getReceiver().equals(contactAndId[i].getId())) {
                                    username = contactAndId[i].getUsername();
                                    found = true;
                                }
                            }
                            if (found) {
                                contactAndChat[contactAndChatSize] = new MyPair(username, 1);
                                contactAndChatSize++;
                            }
                        } else {
                            String chatUsername = new String();
                            //not first message
                            //vad daca e primul mesaj cu persoana respectiva

                            //trebuie sa caul id-ul din chat in contact and chat

                            for (int i = 0; i < contactAndIdSize; i++) {
                                if (chat.getReceiver().equals(contactAndId[i].getId())) {
                                    chatUsername = contactAndId[i].getUsername();
                                }
                            }
                            Boolean found = false;
                            for (int i = 0; i < contactAndChatSize; i++) {
                                if (chatUsername.equals(contactAndChat[i].getKey())) {
                                    contactAndChat[i].setValue(contactAndChat[i].getValue() + 1);
                                    found = true;
                                }
                            }
                            if (!found) {
                                contactAndChat[contactAndChatSize] = new MyPair(chatUsername, 1);
                                contactAndChatSize++;
                            }
                        }
                    }

                }

                for(int i = 0; i < contactAndChatSize; i++){
                    dataEntries.add(new ValueDataEntry(contactAndChat[i].getKey(), contactAndChat[i].getValue()));
                    pie.labels().position("outside");
                }

                pie.data(dataEntries);
                anyChartView.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
