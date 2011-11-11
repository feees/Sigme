function findObjectById(id) {
	var returnVar = null;

	if (document.getElementById)
		returnVar = document.getElementById(id);
	else if (document.all)
		returnVar = document.all[id];
	else if (document.layers)
		returnVar = document.layers[id];

	return returnVar;
}

function goToFirstPage(dataTable) {
	if (dataTable != null) {
		var paginator = dataTable.getPaginator();
		if (paginator != null) paginator.setPage(1);
	}
}

function goToPreviousPage(dataTable) {
	if (dataTable != null) {
		var paginator = dataTable.getPaginator();
		if (paginator != null) {
			var page = paginator.getPreviousPage();
			if (page != null) paginator.setPage(page);
		}
	}
}

function goToNextPage(dataTable) {
	if (dataTable != null) {
		var paginator = dataTable.getPaginator();
		if (paginator != null) {
			var page = paginator.getNextPage();
			if (page != null) paginator.setPage(page);
		}
	}
}

function goToLastPage(dataTable) {
	if (dataTable != null) {
		var paginator = dataTable.getPaginator();
		if (paginator != null) {
			var page = paginator.getTotalPages();
			if (page != null) paginator.setPage(page);
		}
	}
}

