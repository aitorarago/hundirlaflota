package com.example.trabajofinaluf3.Tcp;

import javafx.scene.image.ImageView;

public class BarcoImageView extends Barco{
    ImageView imageView;

    public BarcoImageView(int vida, int tamany, char orientacion,ImageView i) {
        super(vida, tamany, orientacion);
        imageView=i;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
