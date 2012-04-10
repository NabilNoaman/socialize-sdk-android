/*
 * Copyright (c) 2011 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.socialize.Socialize;
import com.socialize.SocializeService;
import com.socialize.config.SocializeConfig;
import com.socialize.log.SocializeLogger;


/**
 * @author Jason Polites
 *
 */
public class ActionDelegateLauncher extends BaseLauncher {
	
	private Launcher actionLauncher;
	private Launcher commentListLauncher;
	private SocializeLogger logger;

	/* (non-Javadoc)
	 * @see com.socialize.launcher.Launcher#launch(android.app.Activity, android.os.Bundle)
	 */
	@Override
	public boolean launch(Activity context, Bundle data) {
		return getLauncher().launch(context, data);
	}

	/* (non-Javadoc)
	 * @see com.socialize.launcher.Launcher#onResult(android.app.Activity, int, int, android.content.Intent, android.content.Intent)
	 */
	@Override
	public void onResult(Activity context, int requestCode, int resultCode, Intent returnedIntent, Intent originalIntent) {
		getLauncher().onResult(context, requestCode, resultCode, returnedIntent, originalIntent);
	}

	/* (non-Javadoc)
	 * @see com.socialize.launcher.Launcher#shouldFinish()
	 */
	@Override
	public boolean shouldFinish() {
		return getLauncher().shouldFinish();
	}
	
	public void setActionLauncher(Launcher actionLauncher) {
		this.actionLauncher = actionLauncher;
	}
	
	public void setCommentListLauncher(Launcher commentListLauncher) {
		this.commentListLauncher = commentListLauncher;
	}
	
	public void setLogger(SocializeLogger logger) {
		this.logger = logger;
	}

	protected Launcher getLauncher() {
		try {
			if(getConfig().getBooleanProperty(SocializeConfig.SOCIALIZE_SHOW_COMMENT_LIST_ON_NOTIFY, false)) {
				return commentListLauncher;
			}
		}
		catch (Exception e) {
			if(logger != null) {
				logger.error("Failed to inspect socialize config", e);
			}
			else {
				e.printStackTrace();
			}
		}

		return actionLauncher;
	}
	
	@Override
	public LaunchListener getLaunchListener() {
		return getLauncher().getLaunchListener();
	}

	@Override
	public void setLaunchListener(LaunchListener listener) {
		getLauncher().setLaunchListener(listener);
	}

	// Mockable
	protected SocializeConfig getConfig() {
		return getSocialize().getConfig();
	}
	
	// Mockable
	protected SocializeService getSocialize() {
		return Socialize.getSocialize();
	}
}