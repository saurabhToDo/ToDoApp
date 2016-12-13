$(function() {
	
	$("body").on('focus', '#dueDate', function() {
		 $( "#dueDate" ).datepicker({
			 dateFormat : "M dd, yy",
				showButtonPanel : false,
				changeYear : true,
				changeMonth:true,
				
				onSelect : function() {
							$( "#dueDate" ).datetimepicker('getDate');
				}
		 });
		 
		 $('#dueDate').attr('readonly', true);
	});
});