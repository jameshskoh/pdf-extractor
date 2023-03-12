# PDF Text Extractor
PDF extractor used to generate text statistics of PDF files. Based on Apache PDFBox.



## User Guide

1. Download or build the latest release of `pdf-extractor-{version}.jar` (the JAR)

2. Move the JAR to folder that is convenient to you

3. Prepare the (relative or absolute) paths for the following

   1. The `{keywords_file}`, e.g. `keywords/keywords.txt`
      * Must be plain text of any extension
   2. A `{pdf_folder}` that contains the PDF files to be extracted, e.g. `pdf/`
      * Only PDF files with a `.pdf` extension will be processed
   3. A `{output_file}` path, e.g. `output/output.xlsx`
      * File name must end with `.xlsx`

4. Open Terminal or Command Prompt and navigate to the folder that contains the JAR

5. Run the JAR with the following command:
   ```bash
   java -jar pdf-extractor-{version}.jar --keyword-file-path {keywords_file} --pdf-folder-path {pdf_folder} --output-file-path {output_file} --parallel --case-sensitive
   ```

   * **Mandatory flags**
     * `--keyword-file-path`: path of `{keywords_file}`
     * `--pdf-folder-path`: path of `{pdf_folder}`
     * `--output-file-path`: path of `{output_file}`
   * **Optional** (but **important**) **flags**
     * `--parallel`: enables parallel processing
       * if this flag is not set, the program uses sequential processing
     * `--case-sensitive`: enables case-sensitive matching
       * if this flag is not set, the program converts both the keywords and the extracted text to lower case before comparing



#### An Example

```bash
java -jar pdf-extractor-2.0.0.jar --keyword-file-path "keywords/keywords.txt" --pdf-folder-path "pdf/" --output-file-path "output/output.xlsx"
```



## Dependencies

1. `org.apache.commons.commons-lang3`
2. `org.apache.pdfbox.pdfbox`
3. `org.apache.poi.poi`
4. `org.apache.poi.poi-ooxml`
5. `org.javatuples.javatuples`

