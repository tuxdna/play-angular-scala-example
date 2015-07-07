var serverUrl = "";
var listTicketsUrl = serverUrl + "/api/tickets";

function loadTickets() {

	$.get(listTicketsUrl, {}, function(data) {

		var html = '<table class="table">';
		var headerRow = "<tr><th>Title</th><th>Status</th></tr>";
		html += headerRow;
		for (t in data) {
			var id = data[t].id;
			var title = data[t].title;
			var status = data[t].status;
			var row = "<tr><td>" + title + "</td><td>" + status + "</td></tr>";
			html += row;
		}
		html += "</table>";

		$("#ticketListingContainer").html(html);

	});

}
