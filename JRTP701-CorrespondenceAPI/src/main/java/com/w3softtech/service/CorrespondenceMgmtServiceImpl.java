package com.w3softtech.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.w3softtech.binding.COSummery;
import com.w3softtech.entity.CitizenAppRegistartionEntity;
import com.w3softtech.entity.CoTriggersEntity;
import com.w3softtech.entity.DcCaseEntity;
import com.w3softtech.entity.EligibilityDetailsEntity;
import com.w3softtech.entity.repository.IApplicationRegistrationRepository;
import com.w3softtech.entity.repository.ICOTriggerRepository;
import com.w3softtech.entity.repository.IDcCaseRepository;
import com.w3softtech.entity.repository.IEligibilityDetermineRepository;
import com.w3softtech.utils.EmailUtils;

@Service
public class CorrespondenceMgmtServiceImpl implements ICorrespondenceMgmtService {

	@Autowired
	private ICOTriggerRepository triggerRepo;
	@Autowired
	private EmailUtils mailUtil;
	@Autowired
	private IEligibilityDetermineRepository elgRepo;
	@Autowired
	private IApplicationRegistrationRepository appRepo;
	@Autowired
	private IDcCaseRepository caseRepo;
	@Autowired
	private EmailUtils emailUtils;
	int pendingTriggers = 0;
	int successTriggers = 0;
	@Override
	public COSummery processPendingTriggers() {
		CitizenAppRegistartionEntity citizenEntity = null;
		EligibilityDetailsEntity elgEntity = null;
	
		// get triggers details based on trigger status
		List<CoTriggersEntity> triggersList = triggerRepo.findByTriggerStatus("pending");
		COSummery summery = new COSummery();
		summery.setTotalTriggers(triggersList.size());
		
		//process the 	triggers in multithreaded env.... using Executor Framework
		ExecutorService executorService= Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Object> pool=new ExecutorCompletionService<>(executorService);
		
		
		for (CoTriggersEntity triggerEntity : triggersList) {
			//start those 10 threads paralelly giving task of executing process trigger execution
			pool.submit( ()->{
				try {
					processTrigger(summery, triggerEntity);
					successTriggers++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					pendingTriggers++;
					e.printStackTrace();
				}
				return null;
			});
		
		} // for
			// prepare COSummery Report

		summery.setSuccessTriggers(successTriggers);
		summery.setPendingTriggers(pendingTriggers);
		return summery;
	}

	private CitizenAppRegistartionEntity processTrigger(COSummery summery, CoTriggersEntity triggerEntity)
			throws Exception {
		CitizenAppRegistartionEntity citizenEntity = null;
		EligibilityDetailsEntity elgEntity = elgRepo.findByCaseNo(triggerEntity.getCaseNo());
		// get appid based on caseno
		Optional<DcCaseEntity> optCases = caseRepo.findById(triggerEntity.getCaseNo());
		if (optCases.isPresent()) {
			DcCaseEntity caseEntity = optCases.get();
			Integer appId = caseEntity.getAppId();
			// get citizen details based appid
			Optional<CitizenAppRegistartionEntity> optCitizen = appRepo.findById(appId);
			if (optCitizen.isPresent()) {
				citizenEntity = optCitizen.get();
			}
		}

		// generate pdf doc having eligibility details and send pdf document to citizen
		// as email msg
		generatePdfAndSendMail(elgEntity, citizenEntity);
		return citizenEntity;
	}

	// helper method to generate pdf doc
	private void generatePdfAndSendMail(EligibilityDetailsEntity elgEntity, CitizenAppRegistartionEntity citizenEntity)
			throws Exception {
		// create Document obj (openpdf)
		Document document = new Document(PageSize.A4);
		// create pdf file to write content to it
		File file = new File(elgEntity.getCaseNo() + ".pdf");
		FileOutputStream fos = null;
		fos = new FileOutputStream(file);

		PdfWriter.getInstance(document, fos);
		// open document
		document.open();
		// define font for the pdf
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);
		font.setColor(Color.RED);
		// create paragraph having content and above font style
		Paragraph para = new Paragraph("Plan Approval/Denial Communication", font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(para);

		PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(70);
		table.setWidths(new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });
		table.setSpacingBefore(2.0f);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(5);

		Font cellfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		cellfont.setColor(Color.BLACK);
		cell.setPhrase(new Phrase("TraceId", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("CaseNo", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("HolderName", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("HolderSSN", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PlanName", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PlanStatus", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PlanStartDate", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PlanEndDate", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("BenifitAmt", cellfont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("DenialReason", cellfont));
		table.addCell(cell);
//add cells to pdftable
		table.addCell(String.valueOf(elgEntity.getEdTraceId()));
		table.addCell(String.valueOf(elgEntity.getCaseNo()));
		table.addCell(elgEntity.getHolderName());
		table.addCell(String.valueOf(elgEntity.getHolderSSN()));
		table.addCell(elgEntity.getPlanName());
		table.addCell(elgEntity.getPlanStatus());
		table.addCell(String.valueOf(elgEntity.getPlanStartDate()));
		table.addCell(String.valueOf(elgEntity.getPlanEndDate()));
		table.addCell(String.valueOf(elgEntity.getBenifitAmt()));
		table.addCell(elgEntity.getDenialReason());
		// add table to document
		document.add(table);
		// close document
		document.close();

		// send pdf document to citizen as email msg
		String subject = "Plan Approval/Denial Mail";
		String body = "Hello Mr/Miss/Mrs. " + citizenEntity.getFullName()
				+ ", This Mail Contains details of plan Approval/Denial";
		mailUtil.sendMail(citizenEntity.getEmail(), subject, body, file);
		updateCoTrigger(elgEntity.getCaseNo(), file);
	}

	private void updateCoTrigger(Integer caseNo, File file) throws Exception {
		// check trigger availability based on case no.
		CoTriggersEntity triggersEntity = triggerRepo.findByCaseNo(caseNo);
		// get byte[] representing pdf document content
		byte[] pdfContent = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(pdfContent);
		if (triggersEntity != null) {
			triggersEntity.setCoNoticePdf(pdfContent);
			triggersEntity.setTriggerStatus("completed");
			triggerRepo.save(triggersEntity);
		}
		fis.close();
	}
}
