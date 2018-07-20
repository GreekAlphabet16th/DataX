package com.cetiti.core.support;

import com.github.pagehelper.PageHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：封装openApi返回结果
 *
 * */
public class ApiResult {

    public enum CODE{
        RET_OK("0"),
        RET_NO("1"),
        RET_DATA_EXIST("3"),
        RET_EXCEPTION("5");

        private String value;
        private CODE(String value){
            this.value = value;
        }
        public String toString(){
            return this.value;
        }
    }

    private Map<String, Object> resultMap = new HashMap<>();

    /**
     * openApi返回结果集
     * */
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    /**
     * 默认错误返回
     * */
    public static ApiResult getDefaultFailResult(){
        ApiResult apiResult = new ApiResult();
        apiResult.setReslutCode(CODE.RET_NO);
        apiResult.setMsg("");
        return apiResult;
    }

    /**
     * 默认返回
     * */
    public static ApiResult getDefaultApiResult(){
        ApiResult apiResult = new ApiResult();
        apiResult.setReslutCode(CODE.RET_OK);
        apiResult.setMsg("");
        return apiResult;
    }

    /**
     * 分页查询范围，参数前端传入<br>
     *
     * @param offset 起始数量
     * @param limit 限制条数
     */
    public static void offsetPage(int offset, int limit) {
        PageHelper.offsetPage(offset, limit);
    }

    /**
     * 分页结果集对象<br>
     *
     * @param list 查询到的分页结果，为 Page 对象
     * @return PageModel<T> 自定义的分页模型，T 为查询的对象
     */
    public static <T> PageModel<T> resultPage(List<T> list){
        return new PageModel<>(list);
    }

    public void setReslutCode(CODE reslutCode){
        resultMap.put("resultCode", reslutCode.toString());
    }

    public <T> void setPageInfo(PageModel<T> pageModel){
        resultMap.put("data", pageModel);
    }

    public void setMsg(Object msg){
        resultMap.put("message", msg);
    }



}
