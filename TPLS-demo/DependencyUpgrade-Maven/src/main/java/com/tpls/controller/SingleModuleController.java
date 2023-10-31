package com.tpls.controller;

import com.tpls.common.Result;
import com.tpls.depModel.Dependency;
import com.tpls.depModel.DependencyView;
import com.tpls.pojo.JsonResult;
import com.tpls.service.SingleModuleService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/single-module")
public class SingleModuleController {
    @Autowired
    private  SingleModuleService singleModuleService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;


    public SingleModuleController(SingleModuleService singleModuleService) {
        this.singleModuleService = singleModuleService;
    }


     /**
      * 上传文件
     * @param file
     * @return
      * */

    @PostMapping("/upload")
    public JsonResult<Map<String,String>> uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request) {

        try {
            if (file.isEmpty()) return new JsonResult<>(-1,"file is empty");

            String uploadDirectory = uploadFolder; // D:/tmp/tpls/upload/
            String parentDir=UUID.randomUUID().toString().substring(0,16);
            String originalName="pom.xml"; //单模块就这么干，把上传的文件都命名为pom.xml，以便学姐的算法找

            String fileUploadDir = uploadDirectory + parentDir ; // 文件夹
            File directory = new File(fileUploadDir);
            if(!directory.exists()){
                directory.mkdirs();
            }

            String filePath = fileUploadDir +  "/" + originalName; // 文件
            file.transferTo(new File(filePath).getAbsoluteFile());

            Map<String,String> res=new HashMap<>();
            res.put("fileDir",fileUploadDir);
            res.put("url","/upload/" + parentDir + "/" + originalName);

            return new JsonResult<>(res); // 返回前后端Url

        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult<>(-1,"file not created");
        }

    }

    /**
     * 获取所有依赖库
     */
    @GetMapping("/libs")
    public JsonResult<List<Dependency>> getlibs(@RequestParam("fileDir")String fileDir){

     if (fileDir == null) return new JsonResult<>(-1,"Uploaded directory not found.");
     return new JsonResult<>(singleModuleService.getLibsFromPom(fileDir));
    }

    /**
     * 获取所有依赖库
     */
    @GetMapping("versions")
    public JsonResult<Map<String,List<String>>> getVersions(){

        System.out.println("103");
        Map<String,List<String>> res= singleModuleService.getAllVersionsFromAllLibs();
        System.out.println(res);
        return new JsonResult<>(res);
    }

    /**
     * 获取单模块升级解决方案
     *
     * @param type 优先级，0~2
     * @param fileDir 绝对路径
     * @return solution列表
     */

    @GetMapping("/upgrade-solutions")
    public JsonResult<Map<String,List<List<DependencyView>>>> getSingleModuleUpgradeSolutions(@RequestParam("fileDir")String fileDir,@RequestParam("type")int type) {

        if (fileDir == null) return new JsonResult<>(-1,"unknown error.");

        List<List<Dependency>> list=singleModuleService.getUpgradeSolutions(fileDir, type);

        Map<String,List<List< DependencyView>>> res=new HashMap<>();

        List<List< DependencyView>> solutions=new ArrayList<>();


        for (List<Dependency> li : list){
            List<DependencyView> solution=new ArrayList<>();
            for(Dependency item : li){
                List<Dependency> temp=item.getExclusionDependency();
                if(temp.size()!=0){
                    List<DependencyView> excludes=new ArrayList<>();
                    for (Dependency ex: temp) excludes.add(new DependencyView(ex.getGroupId(),ex.getArtifactId(), ex.getVersion(), null));
                    solution.add(new DependencyView(item.getGroupId(),item.getArtifactId(),item.getVersion(),excludes));
                }
                else solution.add(new DependencyView(item.getGroupId(),item.getArtifactId(),item.getVersion(),null));
            }
            solutions.add(solution);
        }

        res.put("list",solutions);

        return new JsonResult<>(res);

    }


    /**
     * 获取单模块升级解决方案
     *
     * @param type 优先级，0~2
     * @param fileDir 绝对路径
     * @return solution列表
     */

    @GetMapping("/customized-upgrade-solutions")
    public JsonResult<Map<String,List<List<DependencyView>>>> getCustomizedSingleSolutions(@RequestParam("fileDir")String fileDir,
                                                                                           @RequestParam("type")int type,
                                                                                           @RequestParam("groupId")String groupId,
                                                                                           @RequestParam("artifactId")String artifactId,
                                                                                           @RequestParam("opr")String opr,
                                                                                           @RequestParam("version")String version) {
        System.out.println("========================");
        System.out.println(fileDir);
        System.out.println(type);
        System.out.println(groupId);
        System.out.println(artifactId);
        System.out.println(opr);
        System.out.println(version);
        System.out.println("========================");

        if (fileDir == null) return new JsonResult<>(-1,"unknown error.");

        List<List<Dependency>> list=singleModuleService.getCustomizedUpgradeSolutions(fileDir,type,groupId,artifactId,opr,version);

        Map<String,List<List< DependencyView>>> res=new HashMap<>();

        List<List< DependencyView>> solutions=new ArrayList<>();


        for (List<Dependency> li : list){
            List<DependencyView> solution=new ArrayList<>();
            for(Dependency item : li){
                List<Dependency> temp=item.getExclusionDependency();
                if(temp.size()!=0){
                    List<DependencyView> excludes=new ArrayList<>();
                    for (Dependency ex: temp) excludes.add(new DependencyView(ex.getGroupId(),ex.getArtifactId(), ex.getVersion(), null));
                    solution.add(new DependencyView(item.getGroupId(),item.getArtifactId(),item.getVersion(),excludes));
                }
                else solution.add(new DependencyView(item.getGroupId(),item.getArtifactId(),item.getVersion(),null));
            }
            solutions.add(solution);
        }

        res.put("list",solutions);

        return new JsonResult<>(res);

    }



    /**
     *  生成选定的POM文件
     * @param fileDir
     * @param id
     * @return
     */
    @GetMapping("/pom")
    public JsonResult<Map<String,String>> generatePomFile(@RequestParam("fileDir")String fileDir,@RequestParam("id") int id) {


        if (fileDir == null) return new JsonResult<>(-1,"file is empty");

        String generatedPomFile = singleModuleService.generatePomFile(fileDir, id);
        if (generatedPomFile == null) return new JsonResult<>(-2,"Failed to generate POM file.");

        Map<String,String> res=new HashMap<>();
        res.put("url",generatedPomFile);


        return new JsonResult<>(res);

    }

}
