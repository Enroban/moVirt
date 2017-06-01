package org.ovirt.mobile.movirt.ui.triggers;

import org.ovirt.mobile.movirt.auth.account.data.MovirtAccount;
import org.ovirt.mobile.movirt.auth.account.data.Selection;
import org.ovirt.mobile.movirt.model.trigger.Trigger;
import org.ovirt.mobile.movirt.util.NullPriorityComparator;

class ViewTrigger implements Comparable<ViewTrigger> {
    final Trigger trigger;
    final boolean highlight;

    final MovirtAccount account;
    final String clusterName;
    final String entityName;

    public ViewTrigger(Trigger trigger, boolean highlight, MovirtAccount account, String clusterName, String entityName) {
        this.trigger = trigger;
        this.highlight = highlight;
        this.account = account;
        this.clusterName = clusterName;
        this.entityName = entityName;
    }

    public String getPath() {
        return Selection.getDescription(account, clusterName, entityName);
    }

    @Override
    public int compareTo(ViewTrigger o) {
        int boolCmp = (highlight == o.highlight) ? 0 : (highlight ? -1 : 1);
        if (boolCmp != 0) {
            return boolCmp;
        }

        NullPriorityComparator comparator = new NullPriorityComparator();

        int accountCmp = comparator.compare(account, o.account);
        if (accountCmp != 0) {
            return accountCmp;
        }

        int clusterCmp = comparator.compare(clusterName, o.clusterName);
        if (clusterCmp != 0) {
            return clusterCmp;
        }

        return comparator.compare(entityName, o.entityName);
    }
}
