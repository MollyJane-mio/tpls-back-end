package com.tpls.service;

import com.tpls.core.CustomizedSingleModule;
import com.tpls.core.SingleModule;
import com.tpls.depModel.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SingleModuleService {

    @Autowired
    private CustomizedSingleModule customizedSingleModule;

    /**
     * 获取所有依赖库
     * @param projectPath
     * @return
     */

    public List<Dependency> getLibsFromPom(String projectPath){
        List<Dependency> libs;
        try{
            libs = customizedSingleModule.getLibsFromPom(projectPath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return libs;
    }

    public List<String> getVersions(String groupId,String artifactId){
        List<String> versions;
        try{
            versions=customizedSingleModule.getAllVersions(groupId,artifactId);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return versions;
    }


    public Map<String,List<String>> getAllVersionsFromAllLibs(){
        Map<String,List<String>> versions;
        try{
            versions=customizedSingleModule.getAllVersionsOfAllLib();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return versions;
    }

    /**
     * 根据选择的排序方式（type），生成升级方案(Generate Solutions)
     */

    public List<List<Dependency>> getUpgradeSolutions(String pomDirection, int type) {

        List<List<Dependency>> upgradeSolutions;
        try {
            upgradeSolutions = customizedSingleModule.getSingleUpgradeSolutions(pomDirection, type);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return upgradeSolutions;
    }
    public List<List<Dependency>> getCustomizedUpgradeSolutions(String pomDirection, int type, String groupId, String artifactId,
                                                                String opr, String version) {

        List<List<Dependency>> upgradeSolutions;
        try {
            upgradeSolutions = customizedSingleModule.getCustomizedUpgradeSolutions(pomDirection,type,groupId,artifactId
            ,opr,version);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return upgradeSolutions;
    }


    /**
     * 生成文件（Download Pom）
     */

    public String generatePomFile( String pomPath, int id) {
        try {
            customizedSingleModule.getUpgradedPom(id);
            return pomPath.substring(pomPath.indexOf("/upload"))+"/pom"+id+".xml"; // 返回生成的pom文件的upload版路径

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to generate pom file.";
        }
    }

}
