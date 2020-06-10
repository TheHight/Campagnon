package com.example.campagnon.Class;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.campagnon.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CustomGridAdapter  extends BaseAdapter {

    private List<Produit> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridAdapter(Context aContext,  List<Produit> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.widget_produit_ficheproducteur, null);
            holder = new ViewHolder();
            holder.ProduitImageView = (ImageView) convertView.findViewById(R.id.product_picture);
            holder.ProduitNameView = (TextView) convertView.findViewById(R.id.display_nom_produit_widget);
            holder.ProduitQdispo = (TextView) convertView.findViewById(R.id.textViewQDispo);
            holder.ProduitPrixKG = (TextView) convertView.findViewById(R.id.textViewPrixKG);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Produit produit = this.listData.get(position);
        holder.ProduitNameView.setText(produit.getNom_produit());
        holder.ProduitPrixKG.setText("Prix/kilo : "+produit.getPrix_kg() +"€");
        holder.ProduitQdispo.setText("Quantité dispo : "+ produit.getQté_produit()+"KG");

        int imageId = this.getMipmapResIdByName(produit.getNom_produit());

        holder.ProduitImageView.setImageBitmap(getBitmapfromUrl(produit.getImage()));
        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView ProduitImageView;
        TextView ProduitNameView;
        TextView ProduitQdispo;
        TextView ProduitPrixKG;
    }

    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}