package cn.edu.buaa.practice.bean;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextTextPair implements WritableComparable<TextTextPair> {

	private Text t1;
	private Text t2;

	public TextTextPair() {
		set(new Text(), new Text());
	}

	public TextTextPair(Text t1, Text t2) {
		set(t1, t2);
	}

	public TextTextPair(String t1, String t2) {
		set(new Text(t1), new Text(t2));
	}

	public void set(Text t1, Text t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	public Text getDay() {
		return t1;
	}

	public void setDay(Text t1) {
		this.t1 = t1;
	}

	public Text getHour() {
		return t2;
	}

	public void setHour(Text t2) {
		this.t2 = t2;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		t1.write(out);
		t2.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		t1.readFields(in);
		t2.readFields(in);
	}

	@Override
	public int compareTo(TextTextPair o) {
		int cmp = t1.compareTo(o.t1);
		if (cmp != 0)
			return cmp;
		return t2.compareTo(o.t2);
	}

	@Override
	public int hashCode() {
		return t1.hashCode() * 163 + t2.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TextTextPair) {
			TextTextPair pair = (TextTextPair) obj;
			return t1.equals(pair.t1) && t2.equals(pair.t2);
		}
		return false;
	}
	@Override
	public String toString() {
		return this.t1.toString() + " " + this.t2.toString();
	}

}
