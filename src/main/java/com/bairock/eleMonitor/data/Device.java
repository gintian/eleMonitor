package com.bairock.eleMonitor.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bairock.eleMonitor.Util;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 设备
 * 
 * @author 44489
 *
 */
@Entity
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	// 起始地址
	private int beginAddress;
	// 数据长度
	private int dataLength;
	// 字节顺序
	private int byteOrder;
	// 值类型,整数, 浮点数
	private ValueType valueType = ValueType.VALUE;
	// 值格式
	private ValueFormat valueFormat = ValueFormat.ABS;
	// 系数
	private float coefficient = 1;
	// 单位
	private String unit = "";
	// 排序索引
	private int sortIndex;
	// 组图标路径
	private String icon = "";
	
	private String remark = "";
	
	private float value;

	@ManyToOne
	@JsonIgnore
	private DeviceGroup deviceGroup;

	@ManyToOne
	@JsonBackReference("collector_device")
	private Collector collector;

	@Transient
	private OnValueListener onValueListener;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(int beginAddress) {
		this.beginAddress = beginAddress;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getByteOrder() {
		return byteOrder;
	}

	public void setByteOrder(int byteOrder) {
		this.byteOrder = byteOrder;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public ValueFormat getValueFormat() {
		return valueFormat;
	}

	public void setValueFormat(ValueFormat valueFormat) {
		this.valueFormat = valueFormat;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Collector getCollector() {
		return collector;
	}

	public void setCollector(Collector collector) {
		this.collector = collector;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public DeviceGroup getDeviceGroup() {
		return deviceGroup;
	}

	public void setDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroup = deviceGroup;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		//保留两位小数
		value = Util.scale(value);
		if(null != onValueListener) {
			onValueListener.onValueReceived(this, value);
		}
//		if(Math.abs(value - this.value) >= 0.01) {
			this.value = value;
			if(null != onValueListener) {
				onValueListener.onValueChanged(this, value);
			}
//		}
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	@JsonIgnore
	public String getValueString() {
		String valueString = "";
		switch(valueType) {
		case ALARM :
			if(value == 0) {
				valueString = "正常";
			}else {
				valueString = "异常";
			}
			break;
		case SWITCH :
			if(value == 0) {
				valueString = "关";
			}else {
				valueString = "开";
			}
			break;
		default:
			valueString = String.valueOf(value);
			break;
		}
		return valueString;
	}
	
	public void handler(byte[] byData) {
		float value = 0;
		if(byteOrder == 12) {
			value = bytesToInt12(byData) * coefficient;
		}else {
			value = bytesToInt21(byData) * coefficient;
		}
		setValue(value);
	}
	
	private int bytesToInt12(byte[] by) {
		int value = 0;
		for(int i=0; i < by.length; i++) {
			value = value << 8 | by[i];
		}
		return value;
	}
	
	private int bytesToInt21(byte[] by) {
		int value = 0;
		for(int i=by.length - 1; i >= 0; i--) {
			value = value << 8 | by[i];
		}
		return value;
	}

	public void setOnValueListener(OnValueListener onValueListener) {
		this.onValueListener = onValueListener;
	}

	public interface OnValueListener{
		/**
		 * 设备值改变
		 * @param device
		 * @param value
		 */
		void onValueChanged(Device device, float value);
		/**
		 * 收到设备值, 不一定是改变了
		 * @param device
		 * @param value
		 */
		void onValueReceived(Device device, float value);
	}

}
