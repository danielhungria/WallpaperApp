package br.com.dhungria.wallpaperapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.models.SupportModel
import br.com.dhungria.wallpaperapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun uploadSupport(
        title: String,
        text: String,
        context: Context?
    ) {
        val uuid = UUID.randomUUID().toString()
        val saveSupport = SupportModel(
            id = uuid,
            title = title,
            text = text
        )
        repository.querySupportService().addOnCompleteListener {
            if (it.isSuccessful && !it.result.isEmpty) {
                if (title.isNotBlank() && text.isNotBlank()) {
                    repository.uploadSupport(saveSupport, uuid)
                    context?.let { context ->
                        Toast.makeText(
                            context,
                            context.getString(R.string.success),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    context?.let {
                        Toast.makeText(
                            context,
                            context.getString(R.string.fill_in_all_fields),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                context?.let {
                    Toast.makeText(context, context.getString(R.string.service_unavailable), Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

}