package gt.edu.uvg.myrecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/myrec.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);


    }

    public void  start(View v){
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(this, "Recording", Toast.LENGTH_SHORT).show();
    }

    public void  stop(View v){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder=null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(this, "Recorded", Toast.LENGTH_SHORT).show();


    }

    public void  play(View v) throws IOException{
        MediaPlayer m= new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();


    }

}
