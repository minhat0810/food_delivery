package com.example.dacs3_fodr;

public class Category {
    private int Id;
    private String ImagePath;
    private String Name;

    public Category() {
    }

    public Category(int id, String imagePath, String nameCate) {
        this.Id = id;
        this.ImagePath = imagePath;
        Name = nameCate;
    }

//    public int getId() {
//        return Id;
//    }
//
//    public void setId(int id) {
//        this.Id = id;
//    }
//
//    public String getImagePath() {
//        return ImagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.ImagePath = imagePath;
//    }
//
//    public String getNameCate() {
//        return Name;
//    }
//
//
//    public void setNameCate(String name) {
//        Name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + Id +
//                ", imagePath='" + ImagePath + '\'' +
//                ", NameCate='" + Name + '\'' +
//                '}';
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
