var contextListLoaded=false;

function getTargetRepository() {
  return gadgets.util.unescapeString(prefs.getString("targetRepository"));
}

function getTargetContextPath() {
  var targetContextPath = gadgets.util.unescapeString(prefs.getString("targetContextPath"));
  if (targetContextPath==null || targetContextPath=='') {
    targetContextPath = "/default-domain"; //in Nuxeo pref should be set at creation time
  }
  return targetContextPath;
}

function getTargetContextObject() {
    var targetContextObject = gadgets.util.unescapeString(prefs.getString("targetContextObject"));
    if (targetContextObject==null || targetContextObject=='') {
      targetContextObject = "Domain"; //in Nuxeo pref should be set at creation time
    }
    return targetContextObject;
}

function saveContext() {
 var contextPath = _gel("contextPathChooser").value;
 prefs.set("targetContextPath", contextPath);
 _gel("contextChooser").style.display="none";
}

function displayContextChooser() {

  var query = "select * from ";
  query += getTargetContextObject();
  query += " where ecm:currentLifeCycleState != 'deleted'";

  var ContextRequestParams={ operationId : 'Document.Query',
         operationParams : {query: query},
         operationContext : {},
         operationDocumentProperties : "dublincore",
         entityType : 'documents',
         usePagination : false,
         displayMethod : availableContextsReceived ,
       };

  if (contextListLoaded) {
    showContextPathSelector();
  }
  else {
    doAutomationRequest(ContextRequestParams);
  }
}

function availableContextsReceived(entries, nxParams) {

  var elSel = _gel("contextPathChooser");

  var selectedValue= getTargetContextPath();

  for ( var i = 0; i < entries.length; i++) {

    var elOptNew = document.createElement('option');
    elOptNew.text =  entries[i].title;
    elOptNew.value = entries[i].path;
    if (elOptNew.value==selectedValue) {
      elOptNew.selected=true;
    }
    try {
       elSel.add(elOptNew, null); // standards compliant; doesn't work in IE
    }
    catch(ex) {
       elSel.add(elOptNew); // IE only
    }
  }
  contextListLoaded=true;
  showContextPathSelector();
}

function showContextPathSelector() {
_gel("contextChooser").style.display="block";
}