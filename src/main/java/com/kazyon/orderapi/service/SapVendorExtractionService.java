package com.kazyon.orderapi.service;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.kazyon.orderapi.model.VendorSap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SapVendorExtractionService {

    public static final String FILE_PATH = "C:\\Users\\abdelilah.boulkhayra\\Documents\\SAP\\SAP GUI\\export_vendor_sap.XLSX";
    public static final String FILE_NAME = "export_vendor_sap.XLSX";

    @Autowired
    private VendorSapExcelUploadService vendorSapExcelUploadService;

    public void generateExcelFile() {
        // Check if the file exists and delete if it does
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Existing file deleted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Variables for SAP interaction
        ActiveXComponent SAPROTWr, GUIApp, Connection, Session, Obj;
        Dispatch ROTEntry;
        ComThread.InitSTA();

        try {
            // Set SapGuiAuto = GetObject("SAPGUI")
            SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");
            ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
            Dispatch ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine").toDispatch();
            GUIApp = new ActiveXComponent(ScriptEngine);

            // Set connection = application.Children(0)
            Connection = new ActiveXComponent(GUIApp.invoke("Children", 0).toDispatch());

            // Set Session = connection.Children(0)
            Session = new ActiveXComponent(Connection.invoke("Children", 0).toDispatch());

            // Open transaction

            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/tbar[0]/okcd").toDispatch());
            Obj.setProperty("text", "/nmkvz");
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]").toDispatch());
            Obj.invoke("sendVKey", 0);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/usr/ctxtI_KTOKK-LOW").toDispatch());
            Obj.setProperty("text", "V301");
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/usr/ctxtI_KTOKK-LOW").toDispatch());
            Obj.invoke("setFocus");
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/usr/ctxtI_KTOKK-LOW").toDispatch());
            Obj.setProperty("caretPosition", 4);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]/tbar[1]/btn[8]").toDispatch());
            Obj.invoke("press");
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]").toDispatch());
            Obj.invoke("sendVKey", 46);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[0]").toDispatch());
            Obj.invoke("sendVKey", 43);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[1]/usr/ctxtDY_FILENAME").toDispatch());
            Obj.setProperty("text", FILE_NAME);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[1]/usr/ctxtDY_FILENAME").toDispatch());
            Obj.setProperty("caretPosition", 18);
            Obj = new ActiveXComponent(Session.invoke("findById", "wnd[1]/tbar[0]/btn[0]").toDispatch());
            Obj.invoke("press");

            Thread.sleep(5000);

            // Process the generated file
            try {
                Path path = Paths.get(FILE_PATH);
                if (Files.exists(path)) {
                    System.out.println("File generated successfully: " + FILE_PATH);
                } else {
                    System.out.println("File does not exist: " + FILE_PATH);
                }
            } catch (Exception e) {
                System.out.println("An error occurred while processing the file: " + FILE_PATH);
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ComThread.Release();
        }
    }

    public List<VendorSap> importVendorsFromGeneratedFile() {
        Path path = Paths.get(FILE_PATH);
        return vendorSapExcelUploadService.getVendorsDataFromExcelFile(path);
    }
}
