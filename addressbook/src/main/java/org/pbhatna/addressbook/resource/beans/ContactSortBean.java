package org.pbhatna.addressbook.resource.beans;
import javax.ws.rs.QueryParam;

import org.pbhatna.addressbook.util.Order;
import org.pbhatna.addressbook.util.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactSortBean {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactSortBean.class);
		
	private @QueryParam("sortby") String sort;
	private @QueryParam("orderby") String order;
	
	public String getSort() {
		return sort;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getSortCriteria() {
		logger.debug("sortBy :" + sort);
		return sort;
	}

	public boolean isValid () {
		boolean valid = false;
		
		// Validate if sortby query parameter matches the searching criteria
		for (Sort sort: Sort.values()) {
			if (sort.getValue().equals(this.sort)) {
				logger.info(sort.getValue() + valid);
				valid = true;
			}
		}
		
		// Validate if orderby query parameter matches the correct ordering criteria
		for (Order order: Order.values()) {
			if (order.getValue().equals(order)) {
				logger.info(order.getValue() + valid);
				valid = true;
			}
		}
		
		logger.info("valid :" + valid);
		return valid;
	}

	@Override
	public String toString() {
		return "ContactSortBean [sort=" + sort + ", order=" + order + "]";
	}
}
