package com.linearbd.mixdesign.ui.mixDesign.calculation;


import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.helper.AggregateSize;
import com.linearbd.mixdesign.helper.ConcreteGrade;
import com.linearbd.mixdesign.helper.DryBulkVolByUnitVol;
import com.linearbd.mixdesign.helper.Exposure;
import com.linearbd.mixdesign.helper.HimsWorthConstant;
import com.linearbd.mixdesign.helper.StandardDeviation;
import com.linearbd.mixdesign.helper.WaterAndAirContent;
import com.linearbd.mixdesign.helper.WaterCementFromCompressiveStrength;
import com.linearbd.mixdesign.model.Data;
import com.linearbd.mixdesign.ui.mixDesign.BaseFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Date;

import katex.hourglass.in.mathlib.MathView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationFragment extends BaseFragment implements CalculationContract.View {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private static final int READ_WRITE_PERMISSION=3000;

    private TextView tvStdDev,tvHimsWorth,tvMinStn,tvWaterCementOne,tvWaterCementTwo,tvWaterCementAdopt,
            tvWaterCAirContent,tvCementContent,tvDryBulkVolume,tvDryBulkVolumeTxt,tvCementWeight,tvWaterWeight,tvCAWeight,
            tvCementWeightTwo,tvWaterWeightTwo,tvCAWeightTwo,tvFAWeightTwo,tvFieldTitle,tvCaAbsorption,
            tvWaterOne,tvWaterTwo,tvCementField,tvWaterField,tvCAField,tvFAField;

    private MathView tvCementVolume,mvWaterVolume,mvCAVolume,mvAirVolume,mvTotalAbsVol,mvAbsVolFA,mvWeightFA,
            mvTotalFreeMoisture,mvWtFAField,mvQtyCAAbsrp,mvWtofCAinField;

    private ScrollView mScrollView;

    private CalculationPresenter mPresenter;

    private Button tvDownLoad;


    public CalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CalculationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mScrollView = view.findViewById(R.id.scroll_view);
        tvDownLoad = view.findViewById(R.id.download);
        tvDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                externalStoragePermission();
            }
        });
        tvStdDev = view.findViewById(R.id.std_deviation);
        tvHimsWorth = view.findViewById(R.id.himsworth);
        tvMinStn = view.findViewById(R.id.min_stn);
        tvWaterCementOne = view.findViewById(R.id.water_cement_one);
        tvWaterCementTwo = view.findViewById(R.id.water_cement_two);
        tvWaterCementAdopt = view.findViewById(R.id.water_cement_adopt);
        tvWaterCAirContent = view.findViewById(R.id.water_air_content);
        tvCementContent = view.findViewById(R.id.cement_content);
        tvDryBulkVolume = view.findViewById(R.id.dry_bulk_volume);
        tvDryBulkVolumeTxt = view.findViewById(R.id.dry_bulk_volume_txt);
        tvCementWeight = view.findViewById(R.id.cement_weight);
        tvCementVolume = view.findViewById(R.id.cement_volume);
        tvWaterWeight = view.findViewById(R.id.water_weight);
        mvTotalAbsVol = view.findViewById(R.id.total_abs_vol);
        mvWaterVolume = view.findViewById(R.id.water_volume);
        tvCAWeight = view.findViewById(R.id.ca_weight);
        mvCAVolume = view.findViewById(R.id.ca_volume);
        mvAirVolume = view.findViewById(R.id.air_volume);
        mvAbsVolFA = view.findViewById(R.id.abs_vol_fa);
        mvWeightFA = view.findViewById(R.id.weight_vol_fa);

        tvCementWeightTwo = view.findViewById(R.id.cement_weight_two);
        tvWaterWeightTwo = view.findViewById(R.id.water_weight_two);
        tvCAWeightTwo = view.findViewById(R.id.ca_weight_two);
        tvFAWeightTwo = view.findViewById(R.id.fine_aggregare_two);

        tvFieldTitle = view.findViewById(R.id.field_title);
        mvTotalFreeMoisture = view.findViewById(R.id.total_free_moisture);
        mvWtFAField = view.findViewById(R.id.wt_of_fa_field);
        tvCaAbsorption = view.findViewById(R.id.ca_absorption);
        mvQtyCAAbsrp = view.findViewById(R.id.qty_of_water_absorped_by_ca);
        mvWtofCAinField = view.findViewById(R.id.wt_of_ca_in_field);

        tvWaterOne = view.findViewById(R.id.water_one);
        tvWaterTwo = view.findViewById(R.id.water_two);

        tvCementField = view.findViewById(R.id.cement_weight_field);
        tvWaterField = view.findViewById(R.id.water_weight_field);
        tvCAField = view.findViewById(R.id.ca_weight_field);
        tvFAField = view.findViewById(R.id.fine_aggregare_field);


        mPresenter.initialize(getData());


    }

    @Override
    public void initialize(Data data) {
        String designStn = getDesignStn();
        double std_deviation = StandardDeviation.getStandardDeviation(ConcreteGrade.GRADE_ARRAY[getData().getDesign_stn()]);
        double k = HimsWorthConstant.getValue(5);
        double dStn = Double.parseDouble(designStn.split(" ")[0]);
        double avgStn = dStn+k*std_deviation;
        double waterCementOne = WaterCementFromCompressiveStrength.getWaterCementRatio(avgStn,data.getConcrete_air_type());
        double waterCementTwo = Exposure.getWaterCementRatio(data.getExposure(),data.getConcrete_type());
        String airType = getResources().getStringArray(R.array.air_concrete_type_array)[data.getConcrete_air_type()];
        double waterContent = WaterAndAirContent.getWaterContent(getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]);
        double airContent = WaterAndAirContent.getAirContent(getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),data.getExposure());
        double dryBulkVolume = DryBulkVolByUnitVol.getBulkVolPerUnitVol(AggregateSize.sizeArray[data.getMaz_size_ca()],data.getFm_fa());
        double cementWeight = waterContent/(Math.min(waterCementOne,waterCementTwo));
        double caWeight = dryBulkVolume*data.getBulk_density_ca();
        double combineVolume = ((cementWeight/3.15)+(waterContent/1.00)+(caWeight/data.getSp_gr_ca())+(airContent*1000/100));

        tvStdDev.setText("From above Table assume standard deviation for "+ designStn +" grade concrete is "+std_deviation+" MPa");
        tvHimsWorth.setText("Assume 5% of result are allowed to fall below specified design strength.\nForm above find the value of K = "+String.format("%.2f",k));
        tvMinStn.setText("Calculate Mean Strength from the following formula\n\t" +
                "Mean Strength(fm)= Min Strength(fmin) + K * std_deviation\n\t"+
                "Mean Strength(fm)= "+dStn+" + "+String.format("%.2f",k)+" * "+std_deviation+"\n\t"+
                "Mean Strength(fm)= "+String.format("%.2f",avgStn)+" MPa")
        ;

        tvWaterCementOne.setText("Form above Table Find Water-Cement ratio "+waterCementOne+". For the Avg Strength "+String.format("%.2f",avgStn)+" MPa, and "+airType+" concrete.");
        tvWaterCementTwo.setText("Form above Table Find Water-Cement ratio "+waterCementTwo+". For "+getResources().getStringArray(R.array.exposure_array)[data.getExposure()]+" Exposure, and "+getResources().getStringArray(R.array.concrete_type_array)[data.getConcrete_type()]+" concrete.");
        tvWaterCementAdopt.setText("From above two water-cement ratio "+waterCementOne+" and "+waterCementTwo+" take minimum water-cement ratio = "+Math.min(waterCementOne,waterCementTwo));
        tvWaterCAirContent.setText("From above table find water content "+waterContent+" kg/m3 and air-content "+airContent+"% for slump "+getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]+" and maximum size of aggregate "+getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()]);
        tvCementContent.setText("So,\nRequired Cement Content = Water Content/water-cement ratio\n\t"+
            " = "+waterContent+"/"+(Math.min(waterCementOne,waterCementTwo))+"\n\t"+
            " = "+String.format("%.2f",cementWeight)+" kg/m3");
        tvDryBulkVolume.setText("From above table find the dry bulk volume of coarse aggregates per unit volume of concrete is "+dryBulkVolume);

        tvDryBulkVolumeTxt.setText("So\nWeight of CA = dry bulk volume of CA per unit volume of concrete * Dry Rodded Bulk Density of CA\n\t"+
                " = "+dryBulkVolume+" * "+data.getBulk_density_ca()+"\n\t"+
                " = "+(dryBulkVolume*data.getBulk_density_ca())+" kg/m3"
        );

        tvCementWeight.setText(String.format("%.2f",cementWeight));
        tvCementVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",cementWeight)+"}"+"{"+3.15+"}"+"\\times{10^3} = "+String.format("%.2f",(cementWeight/3.15))+"\\times{10^3}"+"$");

        tvWaterWeight.setText(String.format("%.2f",waterContent));
        mvWaterVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",waterContent)+"}"+"{"+1.00+"}"+"\\times{10^3} = "+String.format("%.2f",(waterContent/1.00))+"\\times{10^3}"+"$");

        tvCAWeight.setText(String.format("%.2f",caWeight));
        mvCAVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",caWeight)+"}"+"{"+String.format("%.2f",data.getSp_gr_ca())+"}"+"\\times{10^3} = "+String.format("%.2f",(caWeight/data.getSp_gr_ca()))+"\\times{10^3}"+"$");
        mvAirVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",airContent)+"}"+"{100}"+"\\times{10^6} = "+String.format("%.2f",(airContent*1000/100))+"\\times{10^3}"+"$");

        mvTotalAbsVol.setDisplayText("$= "+String.format("%.2f",combineVolume)+"\\times{10^3} {cm^3}$");
        mvAbsVolFA.setDisplayText("Threrefore absolute volume of FA"+"$ = "+"(1000-"+String.format("%.2f",combineVolume)+")\\times{10^3}="+String.format("%.2f",(1000-combineVolume))+"\\times{10^3}$");
        mvWeightFA.setDisplayText("So, Weight of FA "+"$ = "+String.format("%.2f",(1000-combineVolume))+"\\times"+data.getSp_gr_fa()+" = "+String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" kg/{m^3}$");

        tvCementWeightTwo.setText(String.format("%.2f",cementWeight)+" Kg");
        tvWaterWeightTwo.setText(String.format("%.2f",waterContent)+" Kg");
        tvCAWeightTwo.setText(String.format("%.2f",caWeight)+" Kg");
        tvFAWeightTwo.setText(String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" Kg");

        tvFieldTitle.setText("The proportions are required to be adjusted for the field conditions. FA has surface" +
                " moisture of "+data.getSurface_moisture_of_fa()+" percent");

        double fieldMoistureContent = (1000-combineVolume)*data.getSp_gr_fa()*data.getSurface_moisture_of_fa()/100;

        mvTotalFreeMoisture.setDisplayText("Total free surface moisture in FA "+"$= \\frac{"+data.getSurface_moisture_of_fa()+"}{100}\\times"
                +String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())
                +" = "+String.format("%.2f",fieldMoistureContent)+" Kg $");

        double fieldFAWeight = (1000-combineVolume)*data.getSp_gr_fa()+fieldMoistureContent;

        mvWtFAField.setDisplayText("Weight of F.A in field condition "+"$="+String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" + "
                +String.format("%.2f",fieldMoistureContent)
                +" = "+String.format("%.2f",((1000-combineVolume)*data.getSp_gr_fa()+fieldMoistureContent))+"Kg$");

        tvCaAbsorption.setText("C.A absorbs "+data.getAbsorption_capacity_of_ca()+ "% water");
        double caAbsorpWater = data.getAbsorption_capacity_of_ca()*caWeight/100;
        mvQtyCAAbsrp.setDisplayText("Quantity of water absorbed by C.A "
                +"$=\\frac {"+data.getAbsorption_capacity_of_ca()+"} {100}" +
                "\\times"+String.format("%.2f",caWeight)+
                " = "+String.format("%.2f",caAbsorpWater)+"Kg$");

        double fieldCAWeignt = caWeight-caAbsorpWater;

        mvWtofCAinField.setDisplayText("Weight of C.A in field condition"
                +"$"+String.format("%.2f",caWeight)+" - "+String.format("%.2f",caAbsorpWater)
                +" = "+String.format("%.2f",(caWeight-caAbsorpWater))+"Kg$");

        tvWaterOne.setText("With regard to water, "+String.format("%.2f",fieldMoistureContent)
                +" kg of water is contributed by F.A and "
                +String.format("%.2f",caAbsorpWater)+" kg of water is " +
                "absorbed by C.A");

        double fieldWaterContent = waterContent-(fieldMoistureContent-caAbsorpWater);

        tvWaterTwo.setText("Therefore "
                +String.format("%.2f",fieldMoistureContent)+" - "+String.format("%.2f",caAbsorpWater)
                +"= "+String.format("%.2f",(fieldMoistureContent-caAbsorpWater))+" kg of extra water is contributed by aggregates. This "
                +"quantity of water is deducted from Total water\n\t\t"
                +String.format("%.2f",waterContent)+" - "+String.format("%.2f",(fieldMoistureContent-caAbsorpWater))
                +" = "+String.format("%.2f",fieldWaterContent)+" Kg"
        );

        tvCementField.setText(String.format("%.2f",cementWeight)+" Kg");
        tvWaterField.setText(String.format("%.2f",fieldWaterContent)+" Kg");
        tvCAField.setText(String.format("%.2f",fieldCAWeignt)+" Kg");
        tvFAField.setText(String.format("%.2f",fieldFAWeight)+" Kg");
        Log.d("YYYYY",waterContent+"");
        Log.d("YYYYY",dryBulkVolume+"");
    }


    @AfterPermissionGranted(READ_WRITE_PERMISSION)
    private void externalStoragePermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            //takeScreenShot(getData().getTitle());

            createPdf();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Location",
                    READ_WRITE_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    private void saveFile(AbstractViewRenderer page){
        new PdfDocument.Builder(getContext()).addPage(page).orientation(PdfDocument.A4_MODE.LANDSCAPE)
                .progressMessage(R.string.gen_pdf_file).progressTitle(R.string.gen_please_wait)
                .renderWidth(2115).renderHeight(1500)
                .saveDirectory(getContext().getExternalFilesDir(null))
                .filename("test")
                .listener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                        Log.d("KKKKKK", "Complete");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                        Log.d("KKKKKK", "Error");
                    }
                }).create().createPdf(getContext());
    }


    private void createPdf(){
        Document document = new Document();

        String file = Environment.getExternalStorageDirectory().getPath()+"/MixDesign";
        File dir1 = new File(file);

        if(dir1.exists()){
            Log.d("HHHH","Directtory Exist "+file);
        }else{
            Log.d("HHHH","Directtory Not Exist "+file);
            dir1.mkdir();
        }

        String filePath = file+"/test.pdf";

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            addMetaData(document);
            addTitlePage(document);
            addContent(document);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addMetaData(Document document){
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Sohel Ahmed");
        document.addCreator("Forbit Tech");

    }

    private void addTitlePage (Document document) throws DocumentException{
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);

        Paragraph title = new Paragraph("Concrete Mix Design",catFont);
        title.setAlignment(Element.ALIGN_CENTER);

        preface.add(title);
        // Lets write a big header
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Design Parameters", subFont));

        addEmptyLine(preface, 1);
        document.add(preface);
        // Will create: Report generated by: _name, _date
        //document.add(new Paragraph("\n"));
        /*preface.add(new Paragraph(
                "Report generated by: " +"Sohel Ahmed" + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        //document.add(new Paragraph("\n\n\n"));

        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);
        //document.add(new Paragraph("\n\n\n\n\n\n"));


        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);*/
        // Start a new page
        //document.newPage();
    }

    private void addContent(Document document) throws DocumentException {

        createInfoTable(document);

    }

    private void createInfoTable (Document document) throws DocumentException {
        //float[] columnWidths = new float[]{10f, 20f, 30f};
        PdfPTable table = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("Item Number"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Parameters"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Value"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);


        table.addCell("1");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        document.add(table);


    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }*/

    private void takeScreenShot(String title) {
        int totalHeight = mScrollView.getChildAt(0).getHeight();
        int totalWidth = mScrollView.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(mScrollView,totalHeight,totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"/data/";
        String fileName = title+".jpg";
        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.d("HHHHH","File Not Found Exception");
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("HHHHH","File Not Found Exception "+e.getMessage());
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
