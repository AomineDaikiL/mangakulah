package id.co.mangakulah.mangaservice.manager;

import java.io.File;

public class ImageFileRenamingManager {

    public void renamingFile(String folder_path){
        File myfolder = new File(folder_path);

        File[] file_array = myfolder.listFiles();

        String baseNum = "00";
        for (int i = 0; i < file_array.length; i++)
        {
            if (file_array[i].isFile())
            {

                File myfile = new File(folder_path + "\\" + file_array[i].getName());
                if (i>=9){
                    baseNum = "0";
                }
                String new_file_name = baseNum+i;
                System.out.println(new_file_name);
                myfile.renameTo(new File(folder_path + "\\" + new_file_name + ".jpg"));
            }
        }
    }
}
