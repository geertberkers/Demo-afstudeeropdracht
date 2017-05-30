package nl.zorgkluis.demo.afstudeeropdracht.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Allergy;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Medication;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Problem;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Procedure;
import nl.zorgkluis.demo.afstudeeropdracht.CDAObjects.Result;
import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class GenericAdapter<T> extends BaseAdapter {

    private final List<T> objectList;

    public GenericAdapter(List<T> objectList) {
        this.objectList = objectList;
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public T getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtView = (TextView) convertView.findViewById(R.id.txtView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        T object = objectList.get(position);

        if (object instanceof Allergy) {
            viewHolder.txtView.setText(((Allergy) object).getAllergen());
        } else if (object instanceof Medication) {
            viewHolder.txtView.setText(((Medication) object).getName());
        } else if (object instanceof Problem) {
            viewHolder.txtView.setText(((Problem) object).getName());
        } else if (object instanceof Procedure) {
            viewHolder.txtView.setText(((Procedure) object).getName());
        } else if (object instanceof Result) {
            viewHolder.txtView.setText(((Result) object).getCaption());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txtView;
    }
}
