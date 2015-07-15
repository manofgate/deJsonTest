package com.example.ben.dejsontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ben on 7/15/2015.
 */
public class bookCustomAdapter extends BaseAdapter {

        private ArrayList<Book> books;
        private Context context;
        private LayoutInflater inflater;

        public bookCustomAdapter(Context context, ArrayList<Book> books) {
            super();
            this.context = context;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.books = books;

        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Book getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(R.layout.book_item, null);


            final Book book = getItem(position);
            if (book != null) {
                TextView title = (TextView) view.findViewById(R.id.titleView);
                TextView author = (TextView) view.findViewById(R.id.authorView);
                ImageView icon = (ImageView) view.findViewById(R.id.iconView);

                if (title != null) {
                    title.setText(book.getTitle());
                }

                if (author != null) {
                    author.setText(book.getAuthor());
                }


                if(icon != null) {
                    if(!book.getImageURL().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(book.getImageURL())
                                .resize(75, 75)
                                .centerCrop()
                                .into(icon);

                    }
                }

            }
            return view;
        }

}

