package ikpmd.westgeestoonk.course_manager.View;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.view.LayoutInflater.Filter;

import java.util.ArrayList;
import java.util.List;

import ikpmd.westgeestoonk.course_manager.Models.Course_Model;
import ikpmd.westgeestoonk.course_manager.R;

public class CourseListAdapter extends ArrayAdapter<Course_Model> {

    public CourseListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Course_Model> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.naam = (TextView) convertView.findViewById(R.id.vak_naam);
            vh.ec = (TextView) convertView.findViewById(R.id.vak_ec);
            vh.jaar = (TextView) convertView.findViewById(R.id.vak_jaar);
            vh.keuze = (TextView) convertView.findViewById(R.id.vak_keuze);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Course_Model c = getItem(pos);
        vh.naam.setText(c.getNaam());
        vh.ec.setText("EC: " + String.valueOf(c.getEC()));
        vh.jaar.setText("Jaar " + c.getJaar());
        if(c.isKeuzeVak()) {
            vh.keuze.setText("Dit is een keuzevak");
        } else {
            vh.keuze.setText("");
        }



        return convertView;
    }

    private static class ViewHolder {
        TextView naam;
        TextView jaar;
        TextView ec;
        TextView keuze;
    }



}
