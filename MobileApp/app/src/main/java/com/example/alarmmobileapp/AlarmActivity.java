package com.example.alarmmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmmobileapp.classes.DayOfWeek;
import com.example.alarmmobileapp.classes.Period;
import com.example.alarmmobileapp.classes.PeriodAdapter;
import com.example.alarmmobileapp.interfaces.RecyclerViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends AppCompatActivity implements RecyclerViewInterface {


    ArrayList<Period> periods = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton addPeriodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setPeriodData();
        recyclerView = findViewById(R.id.PeriodRecycleView);
        PeriodAdapter adapter = new PeriodAdapter(this,periods,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addPeriodButton = findViewById(R.id.addButton);

    }

    public void ReturnToMainActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void CreateNewPeriod(View v){
        Intent intent = new Intent(AlarmActivity.this, CreatePeriodItemActivity.class);
        startActivity(intent);
    }

    private void setPeriodData(){
        List<DayOfWeek>days = new ArrayList<DayOfWeek>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.FRIDAY);
        Period p1 = new Period(0,"ABC","19:20","20:00", days, true);
        Period p2 = new Period(1,"ABasdC","1:20","3:00", days, true);
        Period p3 = new Period(2,"sda","19:20","20:00", days, false);
        Period p4 = new Period(3,"qwea","1:20","10:00", days, true);
        Period p5 = new Period(4,"gaq","19:20","20:00", days, false);
        periods.add(p1);
        periods.add(p2);
        periods.add(p3);
        periods.add(p4);
        periods.add(p5);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AlarmActivity.this, PeriodItemActivity.class);
        intent.putExtra("PERIOD",periods.get(position));
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Period updatedPeriod = (Period) data.getSerializableExtra("PERIOD");
            if (updatedPeriod != null) {
                periods.set(updatedPeriod.getId(),updatedPeriod);
                ((PeriodAdapter) recyclerView.getAdapter()).updatePeriods(periods);
            }
        }
    }
}