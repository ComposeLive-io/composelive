package io.clive.services

import io.clive.CurrentModuleId

internal object MainFunctionExecutorImpl : MainFunctionExecutor {
    override fun runMain(moduleId: String): String? {
        @Suppress("unused") // used by js code
        val localVars = $$"$_$"
        @Suppress("unused") // used by js code
        val jsExportAll = $$"$jsExportAll$"

        CurrentModuleId = moduleId

        val mainFunctionName = js("""
            var module = require(moduleId);
            
            if (!module.hasOwnProperty(jsExportAll)) {
                return null;
            } else {
                function runFirstExportFunction(obj, key, depth, path) {
                    // console.log("JSDebug: Key=" + key + ", path=" + path + ", object=" + obj + ", depth=" + depth);
            
                    if (typeof obj === 'function') {
                        // console.log("JSDebug: found main function - " + path);
                        obj();
                        return key;
                    }
            
                    if (depth > 10) return null;
            
                    if (!obj || typeof obj !== 'object') {
                        return null;
                    }
            
                    var keys = Object.keys(obj);
            
                    for (var i = 0; i < keys.length; i++) {
                        var key = keys[i];
                        var value = obj[key];
            
                        if (key == localVars || key == jsExportAll) {
                            continue;
                        }
            
                        var result = runFirstExportFunction(value, key, depth + 1, path + "." + key);
                        if (result != null) {
                            return result;
                        }
                    }
            
                    return null;
                }
                
                return runFirstExportFunction(module, "not-found", 0, "root");
            }
        """)

        CurrentModuleId = null

        if (mainFunctionName == null || mainFunctionName == "null") return null
        return mainFunctionName
    }
}
