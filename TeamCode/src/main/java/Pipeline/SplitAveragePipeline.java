//package Pipeline;
//
//import org.opencv.core.Mat;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.opencv.core.Core;
//
//public class SplitAveragePipeline extends OpenCvPipeline {
//
//    private Scalar lowerColor;
//    private Scalar upperColor;
//    private Mat hsvImage;
//    private Mat mask;
//
//    String detection_area;
//
//    @Override
//    public Mat processFrame(Mat input) {
//        // Convert input image to HSV
//        hsvImage = new Mat();
//        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_BGR2HSV);
//
//        // Calculate average color within specified zones
//        Scalar averageColorZone1 = Core.mean(new Mat(hsvImage, new Rect(60, 170, 356, 285)));
//        Scalar averageColorZone2 = Core.mean(new Mat(hsvImage, new Rect(735, 170, 253, 230)));
//
//        // Determine the dominant color based on average values
//        if (averageColorZone1.val[0] > averageColorZone2.val[0]) {
//            // Zone 1 is dominant, set thresholds for red
//            lowerColor = new Scalar(0, 100, 100);
//            upperColor = new Scalar(10, 255, 255);
//        } else {
//            // Zone 2 is dominant, set thresholds for blue
//            lowerColor = new Scalar(100, 100, 100);
//            upperColor = new Scalar(120, 255, 255);
//        }
//
//        // Create mask for the determined color
//        mask = new Mat();
//        Core.inRange(hsvImage, lowerColor, upperColor, mask);
//
//        // Create zones
//        Rect zone1Rect = new Rect(60, 170, 356, 285);
//        Rect zone2Rect = new Rect(735, 170, 253, 230);
//
//        // Apply mask to zones
//        Mat zone1 = new Mat(input, zone1Rect);
//        Mat zone2 = new Mat(input, zone2Rect);
//        hsvImage.copyTo(zone1, mask);
//        hsvImage.copyTo(zone2, mask);
//
//        // Release Mats to prevent memory leaks
//        hsvImage.release();
//        mask.release();
//
//        // ------- have some logic -------
//        detection_area = "left";
//        return input;
//    }
//}
