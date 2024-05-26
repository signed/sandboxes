// This is necessary because Typescript refuses to import the file unless it has
// an export apparently. 🥲
export {}

declare global {
  /**
   * The possible types of barcode format that can be detected using the
   * Barcode Detection API. This list may change in the future.
   * Adapted from: https://developer.mozilla.org/en-US/docs/Web/API/Barcode_Detection_API
   */
  type BarcodeFormat =
    | 'aztec'
    | 'code_128'
    | 'code_39'
    | 'code_93'
    | 'codabar'
    | 'data_matrix'
    | 'ean_13'
    | 'ean_8'
    | 'itf'
    | 'pdf417'
    | 'qr_code'
    | 'upc_a'
    | 'upc_e'
    | 'unknown'

  /**
   * The return type of the Barcode Detect API `detect` function that
   * describes a barcode that has been recognized by the API.
   */
  interface DetectedBarcode {
    /**
     * A DOMRectReadOnly, which returns the dimensions of a rectangle
     * representing the extent of a detected barcode, aligned with the
     * image
     */
    boundingBox: DOMRectReadOnly
    /**
     * The x and y co-ordinates of the four corner points of the detected
     * barcode relative to the image, starting with the top left and working
     * clockwise. This may not be square due to perspective distortions
     * within the image.
     */
    cornerPoints: {
      x: number
      y: number
    }[4]
    /**
     * The detected barcode format
     */
    format: BarcodeFormat

    /**
     * A string decoded from the barcode data
     */
    rawValue: string
  }

  /**
   * Options for describing how a BarcodeDetector should be initialised
   */
  interface BarcodeDetectorOptions {
    /**
     * Which formats the barcode detector should detect
     */
    formats: BarcodeFormat[]
  }

  /**
   * The BarcodeDetector interface of the Barcode Detection API allows
   * detection of linear and two dimensional barcodes in images.
   */
  class BarcodeDetector {
    /**
     * Initialize a Barcode Detector instance
     */
    constructor(options?: BarcodeDetectorOptions): BarcodeDetector

    /**
     * Retrieve the formats that are supported by the detector
     */
    static getSupportedFormats(): Promise<BarcodeFormat[]>

    /**
     * Attempt to detect barcodes from an image source
     */
    public detect(source: ImageBitmapSource): Promise<DetectedBarcode[]>
  }

  // Also add the class to the window so we can do feature detection
  interface Window {
    BarcodeDetector: BarcodeDetector
  }
}
