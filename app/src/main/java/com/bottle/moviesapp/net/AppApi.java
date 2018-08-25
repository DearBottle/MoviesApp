package com.bottle.moviesapp.net;


import com.bottle.moviesapp.bean.AdvertAddBean;
import com.bottle.moviesapp.bean.AdvertBean;
import com.bottle.moviesapp.bean.ApplyPermissionBean;
import com.bottle.moviesapp.bean.BaseRequset;
import com.bottle.moviesapp.bean.PayBean;
import com.bottle.moviesapp.bean.PermissionBean;
import com.bottle.moviesapp.bean.RequestUserToken;
import com.bottle.moviesapp.bean.RequsetPermission;
import com.bottle.moviesapp.bean.UserBean;
import com.bottle.moviesapp.bean.UserTokenBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 2018/4/19.
 */

public interface AppApi {

    //登录
    @POST("Login/Index")
    Flowable<DxResponse<UserTokenBean>> login(@Body RequestUserToken requestToken);

    //获取用户信息
    @POST("user/get")
    Flowable<DxResponse<UserBean>> rbac_user(@Body BaseRequset requset);

    //验证用户是否已经购买
    @POST("pay/check")
    Flowable<DxResponse<PayBean>> checkPay(@Body RequsetPermission requsetPermission);

    //验证用户是否已经授权
    @POST("permission/check")
    Flowable<DxResponse<PermissionBean>> checkPermission(@Body RequsetPermission requsetPermission);

    //用户授权
    @POST("permission/apply")
    Flowable<DxResponse<ApplyPermissionBean>> applyPermission(@Body RequsetPermission requsetPermission);

    @POST("advert/get")
    Flowable<DxResponse<List<AdvertBean>>> getAdvert(@Body BaseRequset requset);

    @POST("advert/add")
    Flowable<DxResponse> addAdvert(@Body AdvertAddBean requset);

  /*  //修改密码
    @PUT("dxbase/rbac/user/changePassword")
    Flowable<DxResponse> changePwd(@Body RequestChangePwd changePwdBean);

    //APP版本更新检测
    @GET("dxbase/version/android")
    Flowable<DxResponse<ResponseAppVersion>> checkAppVersion();

    //用户工单列表
    @GET("workflow/myList")
    Flowable<DxResponse<DxPagingResponse<WorkOrderItemBean>>> getWorkOrderAll(@Query(value = "orderBy") String orderBy,
                                                                              @Query(value = "filterBy") String filterBy,
                                                                              @Query("pageSize") int pageSize,
                                                                              @Query("pageIndex") int pageIndex);

    //用户未完成工单列表
    @GET("workflow/doingList")
    Flowable<DxResponse<DxPagingResponse<WorkOrderItemBean>>> getWorkOrderDoing(@Query(value = "orderBy") String orderBy,
                                                                                @Query(value = "filterBy") String filterBy,
                                                                                @Query("pageSize") int pageSize,
                                                                                @Query("pageIndex") int pageIndex);

    //用户已完成工单列表
    @GET("workflow/doneList")
    Flowable<DxResponse<DxPagingResponse<WorkOrderItemBean>>> getWorkOrderDone(@Query(value = "orderBy") String orderBy,
                                                                               @Query(value = "filterBy") String filterBy,
                                                                               @Query("pageSize") int pageSize,
                                                                               @Query("pageIndex") int pageIndex);

    //工单流程详情
    @GET("workflow/flowInstance/{flowInstanceId}")
    Flowable<DxResponse<WorkFlowBean>> getFlowInstance(@Path("flowInstanceId") int flowInstanceId);



    //提交工单
    @POST("workflow/submit/{flowInstanceId}/{stepInstanceId}")
    Flowable<DxResponse<ResponseSubmitOrder>> submitWorkOrder(@Path("flowInstanceId") int flowInstanceId,
                                                              @Path("stepInstanceId") int stepInstanceId,
                                                              @Body RequestSubmitOrder request);



    //上传文件
    @POST("dxbase/upload")
    Flowable<DxResponse<List<ResponseUploadFile>>> submitFiles(
            @Query(value = "resType") String type, @Body MultipartBody files);*/
}

