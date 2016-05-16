package com.sfj.common.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.sfj.R;

import java.io.File;


public class ImageLoaderConfig {


	// SDCard路径
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	// 图片存储路径
	public static final String BASE_PATH = SD_PATH + "/shifangju/";

	// 缓存图片路径
	//图片缓存到SDCard的目录，只需要传入SDCard根目录下的子目录即可，默认会建立在SDcard的根目录下
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";


	/**
	 * 返回默认的参数配置
	 *
	 *            true：显示默认的加载图片 false：不显示默认的加载图片
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// 设置图片缩放方式
		// EXACTLY: 图像将完全按比例缩小的目标大小
		// EXACTLY_STRETCHED: 图片会缩放到目标大小
		// IN_SAMPLE_INT: 图像将被二次采样的整数倍
		// IN_SAMPLE_POWER_OF_2: 图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
		// NONE: 图片不会调整
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {
			// 默认显示的图片
//			bg_image_loading
//			img_search_default
			displayImageOptionsBuilder.showStubImage(R.mipmap.image_default);
			// 地址为空的默认显示图片
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.mipmap.image_default);
			// 加载失败的显示图片
			displayImageOptionsBuilder.showImageOnFail(R.mipmap.image_default);
		}
		// 开启内存缓存
		displayImageOptionsBuilder.cacheInMemory(true);
		displayImageOptionsBuilder.considerExifParams(true);
		// 开启SDCard缓存
		displayImageOptionsBuilder.cacheOnDisc(true);
		// 设置图片的编码格式为RGB_565，此格式比ARGB_8888快
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);

		return displayImageOptionsBuilder.build();
	}

	/**
	 * 返回修改图片大小的加载参数配置
	 * 
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(int targetWidth,
			boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// 设置图片缩放方式
		// EXACTLY: 图像将完全按比例缩小的目标大小
		// EXACTLY_STRETCHED: 图片会缩放到目标大小
		// IN_SAMPLE_INT: 图像将被二次采样的整数倍
		// IN_SAMPLE_POWER_OF_2: 图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
		// NONE: 图片不会调整
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {
			// 默认显示的图片
			displayImageOptionsBuilder.showStubImage(R.mipmap.image_default);
			// 地址为空的默认显示图片
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.mipmap.image_default);
			// 加载失败的显示图片
			displayImageOptionsBuilder
					.showImageOnFail(R.mipmap.image_default);
		}
		// 开启内存缓存
		displayImageOptionsBuilder.cacheInMemory(true);
		// 开启SDCard缓存
		displayImageOptionsBuilder.cacheOnDisc(true);
		// 设置图片的编码格式为RGB_565，此格式比ARGB_8888快
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);
		// 设置图片显示方式
		displayImageOptionsBuilder.displayer(new SimpleImageDisplayer(
				targetWidth));

		return displayImageOptionsBuilder.build();
	}

	/**
	 * 异步图片加载ImageLoader的初始化操作，在Application中调用此方法
	 * 
	 * @param context
	 *            上下文对象

	 *
	 */
	public static void initImageLoader(Context context) {
		// 配置ImageLoader
		// 获取本地缓存的目录，该目录在SDCard的根目录下
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, BASE_IMAGE_CACHE);
		// 实例化Builder
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context);
		// 设置线程数量
		builder.threadPoolSize(3);
		// 设定线程等级比普通低一点
		builder.threadPriority(Thread.NORM_PRIORITY);
		// 设定内存缓存为弱缓存
		builder.memoryCache(new UsingFreqLimitedMemoryCache(10*1024*1024));
		//builder.memoryCache(new UsingFreqLimitedMemoryCache((int) Runtime.getRuntime().maxMemory()/20));
		// 设定内存图片缓存大小限制，不设置默认为屏幕的宽高
		builder.memoryCacheExtraOptions(480, 800);
		// 设定只保存同一尺寸的图片在内存
		builder.denyCacheImageMultipleSizesInMemory();
		// 设定缓存的SDcard目录，UnlimitDiscCache速度最快
		//builder.discCache(new UnlimitedDiskCache(cacheDir));
		// 设定缓存到SDCard目录的文件命名
		builder.discCacheFileNameGenerator(new HashCodeFileNameGenerator());
		// 设定网络连接超时 timeout: 10s 读取网络连接超时read timeout: 60s
		builder.imageDownloader(new BaseImageDownloader(context, 10000, 60000));
		// 设置ImageLoader的配置参数
		builder.defaultDisplayImageOptions(initDisplayOptions(true));
		
//		System.setProperty("http.keepAlive", "false");
		// 初始化ImageLoader
		ImageLoader.getInstance().init(builder.build());
	}
}
