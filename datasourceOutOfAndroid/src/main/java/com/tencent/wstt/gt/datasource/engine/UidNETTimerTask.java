/*
 * Tencent is pleased to support the open source community by making
 * Tencent GT (Version 2.4 and subsequent versions) available.
 *
 * Notwithstanding anything to the contrary herein, any previous version
 * of Tencent GT shall not be subject to the license hereunder.
 * All right, title, and interest, including all intellectual property rights,
 * in and to the previous version of Tencent GT (including any and all copies thereof)
 * shall be owned and retained by Tencent and subject to the license under the
 * Tencent GT End User License Agreement (http://gt.qq.com/wp-content/EULA_EN.html).
 *
 * Copyright (C) 2015 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.tencent.wstt.gt.datasource.engine;

import com.tencent.wstt.gt.datasource.util.UidNETUtils;

import java.util.TimerTask;

/**
 * 指定UID的流量数据采集引擎
 */
public class UidNETTimerTask extends TimerTask {
	private int uid;
	private DataRefreshListener<Double[]> dataRefreshListener;

	/**
	 * 构造方法
	 * @param uid 指定UID
	 * @param dataRefreshListener 数据的监听器
	 */
	public UidNETTimerTask(int uid, DataRefreshListener<Double[]> dataRefreshListener)
	{
		this.uid = uid;
		this.dataRefreshListener = dataRefreshListener;
	}

	public void run() {
		double result[] = {};
		try {
			result = UidNETUtils.getTxRxKB(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Double temp[] = new Double[result.length];
		for (int i = 0; i < result.length; i++)
		{
			temp[i] = Double.valueOf(result[i]);
		}
		dataRefreshListener.onRefresh(System.currentTimeMillis(), temp);
	}

	public void stop()
	{
		this.cancel();
	}
}