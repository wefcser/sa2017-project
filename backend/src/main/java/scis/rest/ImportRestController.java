package scis.rest;

import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;
import scis.storage.StorageException;
import scis.storage.StorageService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/api/import")
public class ImportRestController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher launcher;

    @RequestMapping(value = "" ,method = RequestMethod.GET)
    public @ResponseBody String upload() {
        try {
            File file;
            file = new File("students.xlsx");
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("file",
                    file.getName(), "text/plain", IOUtils.toByteArray(input));
            storageService.store(multipartFile);
            launcher.run(job, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            storageService.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to convert xlxs file.";
        }
        return "messege";
    }

    @RequestMapping(value = "" ,method = RequestMethod.POST)
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file) {
        System.out.println("-----------------------------import");
        if(file==null)return "File is null";
        try {
            storageService.store(file);
            launcher.run(job, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (StorageException e) {
            return "You failed to import " + file.getOriginalFilename() + "!";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to convert xlxs file.");
            return "Failed to convert " + file.getOriginalFilename() + "!";
        }
        storageService.delete();
        String messege="You successfully imported " + file.getOriginalFilename() + "!";
        return messege;
    }
}