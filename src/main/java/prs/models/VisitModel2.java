package prs.models;

import java.sql.Timestamp;
import java.time.LocalDate;

public class VisitModel2 {
	private PatientTableViewModel patient;
	private DoctorModel doctor;
	private long date;
	private String comment;
	private PurposeModel purpose;
	public VisitModel2(PatientTableViewModel patient, DoctorModel doctor, long date, String comment, PurposeModel purpose) {
		this.patient=patient;
		this.doctor=doctor;
		this.date=date;
		this.comment=comment;
		this.purpose=purpose;
	}
	public PatientTableViewModel getPatient() {
		return patient;
	}
	public void setPatient(PatientTableViewModel patient) {
		this.patient = patient;
	}
	public DoctorModel getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorModel doctor) {
		this.doctor = doctor;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public PurposeModel getPurpose() {
		return purpose;
	}
	public void setPurpose(PurposeModel purpose) {
		this.purpose = purpose;
	}
}
