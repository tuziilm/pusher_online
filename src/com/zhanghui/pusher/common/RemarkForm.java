package com.zhanghui.pusher.common;

import com.zhanghui.pusher.domain.RemarkId;

/**
 * ����remark�ı�������
 *
 */
public abstract class RemarkForm<T extends RemarkId> extends IdForm<T>{
    protected String remark;

    @Override
    public T toObj() {
        T t= super.toObj();
        t.setRemark(remark);
        return t;
    }

    public String getRemark() {
		return remark;
	}
    
    public void setRemark(String remark) {
		this.remark = remark;
	}
}