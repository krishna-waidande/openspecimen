
package com.krishagni.catissueplus.core.biospecimen.events;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.StorageContainerPosition;
import com.krishagni.catissueplus.core.administrative.events.StorageLocationSummary;
import com.krishagni.catissueplus.core.biospecimen.domain.Specimen;
import com.krishagni.catissueplus.core.biospecimen.domain.SpecimenRequirement;
import com.krishagni.catissueplus.core.common.AttributeModifiedSupport;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;

@ListenAttributeChanges
public class SpecimenInfo extends AttributeModifiedSupport implements Comparable<SpecimenInfo> {
	private Long id;
	
	private Long cprId;
	
	private Long eventId;
	
	private Long visitId;
	
	private String visitName;
	
	private String cpShortTitle;
	
	private Long reqId;
	
	private Integer sortOrder;
	
	private String label;
	
	private String barcode;

	private String type;
	
	private String specimenClass;
		
	private String lineage;

	private String anatomicSite;

	private String laterality;
	
	private String status;
	
	private String reqLabel;
	
	private String pathology;
	
	private BigDecimal initialQty;
	
	private BigDecimal availableQty;
	
	private Boolean available;
	
	private Double concentration;
	
	private Long parentId;
	
	private String parentLabel;
	
	private StorageLocationSummary storageLocation;
	
	private String storageType;
	
	private String collectionContainer;
	
	private String activityStatus;
	
	private Date createdOn;
	
	private String code;

	private Boolean distributed;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCprId() {
		return cprId;
	}

	public void setCprId(Long cprId) {
		this.cprId = cprId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getCpShortTitle() {
		return cpShortTitle;
	}

	public void setCpShortTitle(String cpShortTitle) {
		this.cpShortTitle = cpShortTitle;
	}

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecimenClass() {
		return specimenClass;
	}

	public void setSpecimenClass(String specimenClass) {
		this.specimenClass = specimenClass;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

	public String getAnatomicSite() {
		return anatomicSite;
	}

	public void setAnatomicSite(String anatomicSite) {
		this.anatomicSite = anatomicSite;
	}

	public String getLaterality() {
		return laterality;
	}

	public void setLaterality(String laterality) {
		this.laterality = laterality;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReqLabel() {
		return reqLabel;
	}

	public void setReqLabel(String reqLabel) {
		this.reqLabel = reqLabel;
	}

	public String getPathology() {
		return pathology;
	}

	public void setPathology(String pathology) {
		this.pathology = pathology;
	}

	public BigDecimal getInitialQty() {
		return initialQty;
	}

	public void setInitialQty(BigDecimal initialQty) {
		this.initialQty = initialQty;
	}

	public BigDecimal getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(BigDecimal availableQty) {
		this.availableQty = availableQty;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentLabel() {
		return parentLabel;
	}

	public void setParentLabel(String parentLabel) {
		this.parentLabel = parentLabel;
	}

	public StorageLocationSummary getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(StorageLocationSummary storageLocation) {
		this.storageLocation = storageLocation;
	}
	
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getCollectionContainer() {
		return collectionContainer;
	}

	public void setCollectionContainer(String collectionContainer) {
		this.collectionContainer = collectionContainer;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getDistributed() {
		return distributed;
	}

	public void setDistributed(Boolean distributed) {
		this.distributed = distributed;
	}

	public static SpecimenInfo from(Specimen specimen) {
		SpecimenInfo info = new SpecimenInfo();
		fromTo(specimen,info);
		return info;
	}
	
	public static List<SpecimenInfo> from(List<Specimen> specimens) {
		List<SpecimenInfo> result = new ArrayList<SpecimenInfo>();
		for (Specimen specimen : specimens) {
			result.add(SpecimenInfo.from(specimen));
		}
		
		return result;
	}

	public static void fromTo(Specimen specimen, SpecimenInfo result) {
		result.setId(specimen.getId());
		
		SpecimenRequirement sr= specimen.getSpecimenRequirement();
		result.setReqId(sr != null ? sr.getId() : null);
		result.setReqLabel(sr != null ? sr.getName() : null);
		result.setSortOrder(sr != null ? sr.getSortOrder() : null);
		result.setLabel(specimen.getLabel());
		result.setBarcode(specimen.getBarcode());
		result.setType(specimen.getSpecimenType());
		result.setSpecimenClass(specimen.getSpecimenClass());
		result.setLineage(specimen.getLineage());
		result.setAnatomicSite(specimen.getTissueSite());
		result.setLaterality(specimen.getTissueSide());
		result.setStatus(specimen.getCollectionStatus());
		result.setPathology(specimen.getPathologicalStatus());
		result.setInitialQty(specimen.getInitialQuantity());
		result.setAvailableQty(specimen.getAvailableQuantity());
		result.setAvailable(specimen.getIsAvailable());
		result.setConcentration(specimen.getConcentration());
		if (specimen.getParentSpecimen() != null) {
			result.setParentId(specimen.getParentSpecimen().getId());
			result.setParentLabel(specimen.getParentSpecimen().getLabel());
		}
	
		StorageLocationSummary location = new StorageLocationSummary();
		StorageContainerPosition position = specimen.getPosition();
		if (position == null) {
			location.setId(-1L);
		} else {
			location.setId(position.getContainer().getId());
			location.setName(position.getContainer().getName());
			location.setPositionX(position.getPosOne());
			location.setPositionY(position.getPosTwo());
		}
		result.setStorageLocation(location);		
		result.setActivityStatus(specimen.getActivityStatus());
		result.setCreatedOn(specimen.getCreatedOn());
		result.setStorageType(sr != null ? sr.getStorageType() : null);
		result.setVisitId(specimen.getVisit().getId());
		result.setVisitName(specimen.getVisit().getName());
		result.setCpShortTitle(specimen.getCollectionProtocol().getShortTitle());
	}	
	
	public static void fromTo(SpecimenRequirement anticipated, SpecimenInfo result) {
		result.setId(null);	
		result.setReqId(anticipated.getId());
		result.setReqLabel(anticipated.getName());
		result.setSortOrder(anticipated.getSortOrder());
		result.setBarcode(null);
		result.setType(anticipated.getSpecimenType());
		result.setSpecimenClass(anticipated.getSpecimenClass());
		result.setLineage(anticipated.getLineage());
		result.setAnatomicSite(anticipated.getAnatomicSite());
		result.setLaterality(anticipated.getLaterality());
		result.setPathology(anticipated.getPathologyStatus());
		result.setInitialQty(anticipated.getInitialQuantity());
		result.setConcentration(anticipated.getConcentration());
		result.setParentId(null);
		result.setCollectionContainer(anticipated.getCollectionContainer());
	
		StorageLocationSummary location = new StorageLocationSummary();
		result.setStorageLocation(location);
		result.setStorageType(anticipated.getStorageType());
	}	
	
	@Override
	public int compareTo(SpecimenInfo other) {
		if (sortOrder != null && other.sortOrder != null) {
			return sortOrder.compareTo(other.sortOrder);
		} else if (sortOrder != null) {
			return -1;
		} else if (other.sortOrder != null) {
			return 1;
		} else if (reqId != null && other.reqId != null && reqId != other.reqId) {
			return reqId.compareTo(other.reqId);
		} else if (reqId == other.reqId) {
			return id.compareTo(other.id);
		} else if (reqId != null) {
			return -1;
		} else if (other.reqId != null) {
			return 1;
		} else if (id != null && other.id != null) {
			return id.compareTo(other.id);
		}
		
		// 
		// TODO: ERROR: need to put a logger here
		// This scenario should not happen, as this means we neither have
		// anticipated specimen nor actual specimen
		//
		return 0;
	}	
		
	public static void orderByLabels(final List<SpecimenInfo> list, final List<String> labels) {
		Collections.sort(list, new Comparator<SpecimenInfo>() {
			@Override
			public int compare(SpecimenInfo o1, SpecimenInfo o2) {
				// Don't think indexOf will have perf impact as long as input labels list is within 500 limit
				int idx1 = labels.indexOf(o1.getLabel()); 
				int idx2 = labels.indexOf(o2.getLabel());
				return idx1 - idx2;
			}			
		});
	}
}
