package io.github.praanto__samadder.cocoa.task_editor

import android.content.Context
import android.widget.ArrayAdapter
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.model.Session

object PredefinedLists {
    fun getPhysicsTopicsList() : ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Gravitational Field")
        list.add("Gravitational Field & Circular Motion")
        list.add("Circular Motion")
        list.add("Ideal Gas")
        list.add("Temperature")
        list.add("Thermal Properties of Material")
        list.add("Remote sensing")
        list.add("Communications")
        list.add("Capacitance")
        list.add("Electric Fields")
        list.add("Oscillations")
        list.add("Paper 5: Planning")
        list.add("Paper 5: Solution")

        return list
    }
    fun getStatisticsTopicsList() : ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Oscillations")
        list.add("Paper 5: Planning")
        list.add("Paper 5: Solution")

        return list
    }
    fun getPureMathsTopicsList() : ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Algebra")
        list.add("Logarithm and Exponential Function")
        list.add("Trigonometry")
        list.add("Differentiation")
        list.add("Integration")
        list.add("Numerical Solutions of Equations")
        list.add("Further Algebra")
        list.add("Further Calculus")
        list.add("Vectors")
        list.add("Differential Equations")
        list.add("Complex Numbers")
        list.add("Paper 5: Planning")
        list.add("Paper 5: Solution")

        return list
    }
    fun getChemistryTopicsList() : ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Organic")
        list.add("Inorganic")
        list.add("Lattice Energy")
        list.add("Electrochemistry")
        list.add("Further Aspects of Equilibrium")
        list.add("Reaction Kinetics")
        list.add("Enthropy and Gibbs Free Energy")
        list.add("Transition Elements")
        list.add("Benzene")
        list.add("Benzene and It's Compounds")
        list.add("Carboxylic Acids and Their Derivatives")
        list.add("Organic Nitrogen Compounds")
        list.add("Polymerisation")
        list.add("Analytic Chemistry")
        list.add("Organic Synthesis")
        list.add("Paper 5: Planning")
        list.add("Paper 5: Solution")

        return list
    }

    fun getSessionsAdapter(context: Context) : ArrayAdapter<String> {
        val list = ArrayList<String>()
        list.add("Session")
        list.add(Session.feb)
        list.add(Session.may)
        list.add(Session.nov)
        return ArrayAdapter(context, R.layout.spinner_text_view_list_item, R.id.text1, list)
    }
}